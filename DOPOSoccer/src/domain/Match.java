package domain;

import java.awt.Dimension;

public abstract class Match {
    public static final int LEFT_SIDE = 1;
    public static final int RIGHT_SIDE = 2;
    private static final double START_X_RATIO = 0.2;       
    public final Dimension RESOLUTION;

    public static final int WINNER_POINTS = 100;
    public static final int LOSER_POINTS = 25;


    private static final double PLAYER_BOUNCING = 0.3; //Rango entre 0 y 1

    private Player leftPlayer;
    private Player rightPlayer;
    private Field field;
    private Ball ball;

    private boolean isFinished;
    private int winnerSide = 0;
    private int matchTimeSec = 0;

    public Match(String condition, Dimension resolution) throws DopoSoccerException{
        setCondition(condition);

        this.RESOLUTION = resolution;
        this.field = new Field(resolution);
        this.ball = new Ball(field); // El balon se va a crear en el centro del campo

    }

    public abstract void setCondition(String condition) throws DopoSoccerException;

    public void addHuman(int side, String character, Profile profile) throws DopoSoccerException{
        if(side == LEFT_SIDE) {
            leftPlayer = new Human(character, startX(side), startY(), field);
            leftPlayer.setProfile(profile);
        }
        else if (side == RIGHT_SIDE){
            rightPlayer = new Human(character, startX(side), startY(), field);
            rightPlayer.setProfile(profile);
        }
        else throw new DopoSoccerException(DopoSoccerException.INVALID_SITE);
    }

    private double startX(int side){
        double margin = field.getWidth() * START_X_RATIO;
        if(side == LEFT_SIDE) return margin;
        return field.getWidth() - margin - playerSize();
    }

    private double startY(){
        return field.getGroundY() - playerSize();
    }

    public void update(){
        if (isFinished) return;

        leftPlayer.applyGravity();
        rightPlayer.applyGravity();
        ball.move();

        collision(leftPlayer);
        collision(rightPlayer);

        ball.keepInsideField();

        leftPlayer.endFrame();
        rightPlayer.endFrame();

        checkGoal();
        checkFinished();
        matchTimeSec++;
    }

    public void kick(int side){
        if (isFinished) return;
        int direction;
        if(side == LEFT_SIDE) direction = 1;
        else direction = -1;
        getPlayer(side).kick(ball, direction);
    }

    public Player getPlayer(int side){
        if(side == LEFT_SIDE) return leftPlayer;
        else if(side == RIGHT_SIDE) return rightPlayer;

        else throw new IllegalArgumentException("Lado inválido: " + side);
    }

    private int playerSize(){
        return (int) field.scale(Player.PLAYER_SIZE);
    }

    public Field getField(){
        return field;
    }

    public Ball getBall(){
        return ball;
    }

    private void collision(Player player){
        Vector2D distance  = Vector2D.subtract(ball.center(), player.center());
        double length = Vector2D.length(distance); //distancia entre los centros
        double minDistance = (player.getSize() + ball.getDiameter()) / 2.0; //minima distancia sin colision

        if (length >= minDistance) return; //No existe colision

        else{ //Si existe colision
            Vector2D collisionDirection;
            if(length == 0) collisionDirection = new Vector2D(0, -1); //evitar division entre 0.  -1 para moverse hacia arriba
            else collisionDirection = Vector2D.scalar(distance, 1 / length);

            //Evitar que queden superpuesto uno sobre el otro
            double overlap = minDistance - length; //Cuanto se esta superponiendo
            ball.translate(collisionDirection.getX() * overlap, collisionDirection.getY() * overlap);  
            
            //Rebotar
            Vector2D ballSpeed = Vector2D.subtract(ball.getVelocity(), player.getVelocity()); //Velocidad del balon vista desde el jugador

            double approach = Vector2D.dot(ballSpeed, collisionDirection); // - el balon va hacia el jugador + el balon se esta alejando
            if(approach < 0){                                  
                Vector2D change = Vector2D.scalar(collisionDirection, -(1 + PLAYER_BOUNCING) * approach); //empujon en direccion del choque
                ball.setVelocity(Vector2D.add(ball.getVelocity(), change)); //Nueva velocidad del balon tras el impacto
            }
        }
    }


    private void checkGoal(){
        Vector2D ballCenter = ball.center();
        //gol en arco izquierdo por jugador derecho
        if(field.isGoal(LEFT_SIDE, ballCenter.getX(), ballCenter.getY())) goalScored(rightPlayer);
        
        else if(field.isGoal(RIGHT_SIDE, ballCenter.getX(), ballCenter.getY())) goalScored(leftPlayer);
    }

    private void goalScored(Player player){
        player.scoredGoal();
        kickOff();
    }

    private void kickOff(){
        leftPlayer.kickOffPosition(startX(LEFT_SIDE), startY());
        rightPlayer.kickOffPosition(startX(RIGHT_SIDE), startY());

        ball.kickOff();
    }

    protected abstract void checkFinished();

    protected void finish(int winner){
        if (isFinished) return;

        isFinished = true;
        winnerSide = winner;

        int loser;
        if(winner == LEFT_SIDE) loser = RIGHT_SIDE;
        else loser = LEFT_SIDE;

        getPlayer(winnerSide).getProfile().addPoints(WINNER_POINTS);
        getPlayer(loser).getProfile().addPoints(LOSER_POINTS);
    }

    public boolean isFinished(){
        return isFinished;
    }

    public int getWinner(){
        return winnerSide;
    }

    public abstract String textUnderScore();

    public int getPointsEarned(int side){
        if (!isFinished) return 0;
        else{
            if(side == winnerSide) return WINNER_POINTS;
            else return LOSER_POINTS;
        }
    }

    public int getTime(){
        return matchTimeSec;
    }
}
