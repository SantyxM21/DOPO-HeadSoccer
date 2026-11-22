package domain;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Match implements Serializable{
    public static final int LEFT_PLAYER = 1;
    public static final int RIGHT_PLAYER = 2;
    private static final double START_X_RATIO = 0.2;
    public final Dimension RESOLUTION;

    public static final int WINNER_POINTS = 100;
    public static final int LOSER_POINTS = 25;

    public static final int FPS = 85;                      // veces por segundo que se llama a update()
    public static final double FRAME_SECONDS = 1.0 / FPS;  // duracion ideal de un frame


    private static final double PLAYER_BOUNCING = 0.5   ; //Rango entre 0 y 1

    private Player leftPlayer;
    private Player rightPlayer;
    private Effect leftEffect;
    private Effect rightEffect;
    private Field field;
    private Ball ball;
    private List<FakeBall> fakeBalls = new ArrayList<>();  // solo se dibujan, no chocan con los jugadores ni marcan goles

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

        leftPlayer.act(this, seconds);          // las maquinas deciden antes de la fisica y se les pasa match para que puedan obtener info de la partida
        rightPlayer.act(this, seconds);

        leftPlayer.update(seconds);
        rightPlayer.update(seconds);
        updateEffect(leftPlayer, seconds);
        updateEffect(rightPlayer, seconds);

        leftPlayer.applyGravity();
        rightPlayer.applyGravity();
        playersCollision();
        ball.move();
        updateFakeBalls(seconds);

        ballCollision(leftPlayer);
        ballCollision(rightPlayer);

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
        if(side == LEFT_PLAYER) leftPlayer = new Human(Character_.create(character), startX(side), startY(), field, profile);
        else if (side == RIGHT_PLAYER) rightPlayer = new Human(Character_.create(character), startX(side), startY(), field, profile);
        else throw new DopoSoccerException(DopoSoccerException.INVALID_SITE);
    }

    public void addMachine(int side, String character, Profile profile, int machineType) throws DopoSoccerException{
        Machine newMachine;
        if(machineType == Machine.DUMB) newMachine = new DumbMachine(Character_.create(character), startX(side), startY(), field, profile);
        else if(machineType == Machine.OFENSIVE) newMachine = new OfensiveMachine(Character_.create(character), startX(side), startY(), field, profile);
        else if(machineType == Machine.DEFENSIVE) newMachine = new DefensiveMachine(Character_.create(character), startX(side), startY(), field, profile);
        else if(machineType == Machine.EXPERT) newMachine = new ExpertMachine(Character_.create(character), startX(side), startY(), field, profile);
        else throw new DopoSoccerException(DopoSoccerException.INVALID_MACHINE);

        if(side == LEFT_PLAYER) leftPlayer = newMachine;
        else if (side == RIGHT_PLAYER) rightPlayer = newMachine;
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
        kick(getPlayer(side));
    }

    // Las maquinas patean por aqui para que tambien se revise el aturdimiento del rival
    public void kick(Player player){
        if (isFinished || ball.hasPower()) return;
        Player rival = rivalOf(player);
        player.kick(ball, rival, directionOf(player));
        if (ball.hasPower()) releaseFakeBalls(player);         // la patada salio como tiro de poder
        checkStun(rival);
    }

    private Player rivalOf(Player player){
        if (player == leftPlayer) return rightPlayer;
        return leftPlayer;
    }

    /*
    This method tells on which side a player of this match plays
    @param player the player
    @return LEFT_PLAYER or RIGHT_PLAYER
    */
    public int sideOf(Player player){
        if (player == leftPlayer) return LEFT_PLAYER;
        return RIGHT_PLAYER;
    }

    /*
    This method gives the side of the rival of the given side
    @param side LEFT_PLAYER or RIGHT_PLAYER
    @return the other side
    */
    public static int rivalSide(int side){
        if (side == LEFT_PLAYER) return RIGHT_PLAYER;
        return LEFT_PLAYER;
    }

    /*
    This method tells to which direction a player attacks 
    @param player the player
    @return 1 if it attacks to the right (left player), -1 if it attacks to the left (right player)
    */
    public int directionOf(Player player){
        if (player == leftPlayer) return 1;
        return -1;
    }

    // ==================== COLISIONES Y PODERES ====================

    private void playersCollision(){
        if (leftPlayer.isTouching(rightPlayer)) separatePlayers();
    }

    private void separatePlayers(){
        Vector2D distance = Vector2D.subtract(rightPlayer.center(), leftPlayer.center());
        double length = Vector2D.length(distance);
        double minDistance = leftPlayer.radius() + rightPlayer.radius();

        Vector2D direction;
        if (length == 0) direction = new Vector2D(1, 0);
        else direction = Vector2D.scalar(distance, 1 / length);

        double overlap = minDistance - length;
        Vector2D leftBefore = leftPlayer.center();
        leftPlayer.translate(-direction.getX() * overlap / 2, -direction.getY() * overlap / 2);

        // si uno choca con la pared o el suelo, el otro se mueve lo que falte
        double leftMoved = -Vector2D.dot(Vector2D.subtract(leftPlayer.center(), leftBefore), direction);
        double remaining = overlap - leftMoved;
        Vector2D rightBefore = rightPlayer.center();
        rightPlayer.translate(direction.getX() * remaining, direction.getY() * remaining);

        double rightMoved = Vector2D.dot(Vector2D.subtract(rightPlayer.center(), rightBefore), direction);
        remaining -= rightMoved;
        if (remaining > 0) leftPlayer.translate(-direction.getX() * remaining, -direction.getY() * remaining);
    }

    private void ballCollision(Player player){
        if (!ball.isTouching(player)) return;
        bounceBall(player);
        touchBall(player);      // despues del rebote para que no sobrescriba la velocidad del poder
    }

    private void bounceBall(Player player){
        Vector2D distance  = Vector2D.subtract(ball.center(), player.center());
        double length = Vector2D.length(distance);
        double minDistance = player.radius() + ball.radius(); //minima distancia sin colision

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

    private void touchBall(Player player){
        if(!ball.hasPower() && player.hasAura()){                                       // Si el jugador tiene aura aplica el poder
            player.powerShoot(ball, directionOf(player));
            releaseFakeBalls(player);
        }
        else if(ball.getChargedBy() != player && ball.hasPower()){  // Si golpea al rival y el balon tiene poder
            powerPunch(player);                                     // entonces empuja al jugador impactado
        }
    }

    private void powerPunch(Player victim){
        Player shooter = ball.getChargedBy();
        Power power = ball.getChargedPower();
        ball.clearCharge();

        applyEffect(victim, power.hit(victim, shooter), -directionOf(victim)); //empuja hacia su arco
        checkStun(victim);                                      // si el tiro lo deja sin vida, el aturdimiento reemplaza al efecto del poder
    }

    /* Si la vida del jugador llego a cero por los golpes del rival, queda aturdido */
    private void checkStun(Player victim){
        if (victim.getHealth() == 0) applyEffect(victim, new StunEffect(), 0);
    }

    public void applyEffect(Player target, Effect effect, int direction){
        if (effectOf(target) != null) removeEffect(target);     // el nuevo efecto reemplaza al anterior
        setEffect(target, effect);
        effect.applyEffect(target, direction, field);
    }

    private void updateEffect(Player target, double seconds){
        Effect effect = effectOf(target);
        if (effect == null) return;
        effect.update(target, seconds);
        if (effect.isFinished()) removeEffect(target);
    }

    private void removeEffect(Player target){
        effectOf(target).end(target);                           // cada efecto sabe como devolver al jugador a la normalidad
        setEffect(target, null);
    }

    private Effect effectOf(Player player){
        if (player == leftPlayer) return leftEffect;
        return rightEffect;
    }

    private void setEffect(Player player, Effect effect){
        if (player == leftPlayer) leftEffect = effect;
        else rightEffect = effect;
    }

    public boolean hasEffect(int side){
        return effectOf(getPlayer(side)) != null;
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

    protected void kickOff(){
        resetPlayer(leftPlayer, LEFT_PLAYER);
        resetPlayer(rightPlayer, RIGHT_PLAYER);

        ball.kickOff();
        fakeBalls.clear();
    }

    // ==================== BALONES FALSOS ====================

    private void releaseFakeBalls(Player shooter){
        fakeBalls = ball.getChargedPower().createFakes(ball, shooter, directionOf(shooter), field);
    }

    private void updateFakeBalls(double seconds){
        for (FakeBall fake : fakeBalls){
            fake.move();
            fake.keepInsideField();
            fake.update(seconds);
        }
        fakeBalls.removeIf(FakeBall::isFinished);
    }

    public List<FakeBall> getFakeBalls(){
        return new ArrayList<>(fakeBalls);
    }

    private void resetPlayer(Player player, int side){
        player.setXPosition(startX(side));
        player.setYPosition(startY());
        player.setVelocity(new Vector2D(0, 0));
        if (effectOf(player) != null) removeEffect(player);
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
