package domain;

import java.awt.Dimension;

public abstract class Match {
    public static final int LEFT_PLAYER = 1;
    public static final int RIGHT_PLAYER = 2;
    private static final double START_X_RATIO = 0.2;
    public final Dimension RESOLUTION;

    public static final int WINNER_POINTS = 100;
    public static final int LOSER_POINTS = 25;

    public static final int FPS = 85;                      // veces por segundo que se llama a update()
    public static final double FRAME_SECONDS = 1.0 / FPS;  // duracion ideal de un frame


    private static final double PLAYER_BOUNCING = 0.3; //Rango entre 0 y 1

    private Player leftPlayer;
    private Player rightPlayer;
    private Field field;
    private Ball ball;

    private boolean isFinished;
    private int winnerSide = 0;
    private double elapsedSeconds = 0;                     // tiempo real jugado

    public Match(String condition, Dimension resolution) throws DopoSoccerException{
        setCondition(condition);

        this.RESOLUTION = resolution;
        this.field = new Field(resolution);
        this.ball = new Ball(field); // El balon se va a crear en el centro del campo

    }

    public abstract void setCondition(String condition) throws DopoSoccerException;

    // ==================== CICLO DE LA PARTIDA ====================

    public void update(double seconds){
        if (isFinished) return;

        leftPlayer.update(seconds);
        rightPlayer.update(seconds);

        leftPlayer.applyGravity();
        rightPlayer.applyGravity();
        ball.move();

        collision(leftPlayer);
        collision(rightPlayer);

        ball.keepInsideField();

        leftPlayer.endFrame();
        rightPlayer.endFrame();

        checkGoal();
        updateTime(seconds);
        checkFinished();        // despues de updateTime para que TimeMatch vea el tiempo de este frame
    }

    public void updateTime(double seconds){
        elapsedSeconds += seconds;
    }

    // ==================== JUGADORES =====================

    public void addHuman(int side, String character, Profile profile) throws DopoSoccerException{
        if(side == LEFT_PLAYER) leftPlayer = new Human(character, startX(side), startY(), field, profile);
        else if (side == RIGHT_PLAYER) rightPlayer = new Human(character, startX(side), startY(), field, profile);
        else throw new DopoSoccerException(DopoSoccerException.INVALID_SITE);
    }

    public Player getPlayer(int side){
        if(side == LEFT_PLAYER) return leftPlayer;
        else if(side == RIGHT_PLAYER) return rightPlayer;

        else throw new IllegalArgumentException("Lado inválido: " + side);
    }

    private double startX(int side){
        double margin = field.getWidth() * START_X_RATIO;
        if(side == LEFT_PLAYER) return margin;
        return field.getWidth() - margin - playerSize();
    }

    private double startY(){
        return field.getGroundY() - playerSize();
    }

    private int playerSize(){
        return (int) field.scale(Player.PLAYER_SIZE);
    }

    // ==================== ACCIONES DEL JUGADOR ====================

    public void kick(int side){
        if (isFinished) return;
        int direction;
        if(side == LEFT_PLAYER) direction = 1;
        else direction = -1;
        getPlayer(side).kick(ball, direction);
    }

    // ==================== COLISIONES Y PODERES ====================

    private void collision(Player player){
        Vector2D distance  = Vector2D.subtract(ball.center(), player.center());
        double length = Vector2D.length(distance); //distancia entre los centros
        double minDistance = (player.getSize() + ball.getDiameter()) / 2.0; //minima distancia sin colision

        if (length >= minDistance) return; //No existe colision

        else{ //Si existe colision
            touchBall(player);


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

    private void touchBall(Player player){
        if (ball.getChargedPower() == null){
            //si toca el balon con aura hace el tiro de su poder
            if (player.hasAura()){
                int direction;
                if (player == leftPlayer) direction = 1;
                else direction = -1;

                player.powerShoot(ball, direction);
            }
        } else if (ball.getChargedBy() != player){
            powerPunch(player); //golpear al rival
        }
    }

    private void powerPunch(Player victim){
        Player shooter = ball.getChargedBy();
        Power power = ball.getChargedPower();
        ball.clearCharge();

        victim.takeDamage(power.getShootDamage() * shooter.getKickMultiplier());

        int pushDirection;
        if (victim == leftPlayer) pushDirection = -1;
        else pushDirection = 1;

        victim.receiveEffect(power.getEffect(), pushDirection); //empuja hacia su arco
    }

    // ==================== GOLES Y SAQUE ====================

    private void checkGoal(){
        Vector2D ballCenter = ball.center();
        //gol en arco izquierdo por jugador derecho
        if(field.isGoal(LEFT_PLAYER, ballCenter.getX(), ballCenter.getY())) goalScored(rightPlayer);

        else if(field.isGoal(RIGHT_PLAYER, ballCenter.getX(), ballCenter.getY())) goalScored(leftPlayer);
    }

    private void goalScored(Player scoredBy){
        Player receiver;
        if (scoredBy == leftPlayer) receiver = rightPlayer;
        else receiver = leftPlayer;
        scoredBy.scoredGoal();
        receiver.powerBarReceivedGoal();
        kickOff();
    }

    private void kickOff(){
        resetPlayer(leftPlayer, LEFT_PLAYER);
        resetPlayer(rightPlayer, RIGHT_PLAYER);

        ball.kickOff();
    }

    private void resetPlayer(Player player, int side){
        player.setXPosition(startX(side));
        player.setYPosition(startY());
        player.setVelocity(new Vector2D(0, 0));
    }

    // ==================== FIN DE LA PARTIDA ====================

    protected abstract void checkFinished();

    protected void finish(int winner){
        if (isFinished) return;

        isFinished = true;
        winnerSide = winner;

        int loser;
        if(winner == LEFT_PLAYER) loser = RIGHT_PLAYER;
        else loser = LEFT_PLAYER;

        getPlayer(winnerSide).getProfile().addPoints(WINNER_POINTS);
        getPlayer(loser).getProfile().addPoints(LOSER_POINTS);
    }

    public boolean isFinished(){
        return isFinished;
    }

    public int getWinner(){
        return winnerSide;
    }

    public int getPointsEarned(int side){
        if (!isFinished) return 0;
        else{
            if(side == winnerSide) return WINNER_POINTS;
            else return LOSER_POINTS;
        }
    }

    // ==================== ESTADO DE LA PARTIDA ====================

    public abstract String textUnderScore();

    public int getTime(){
        return (int) elapsedSeconds;
    }

    public int getPlayerGoals(int side){
        return getPlayer(side).getGoals();
    }

    public Field getField(){
        return field;
    }

    public Ball getBall(){
        return ball;
    }
}
