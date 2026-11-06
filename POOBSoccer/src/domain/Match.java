package domain;

public class Match {
    private Player leftPlayer;
    private Player rightPlayer;
    private Field field;
    private Ball ball;

    public Match(Player lfPlayer, Player rgPlayer, Field field, Ball ball){
        this.leftPlayer = lfPlayer;
        this.rightPlayer = rgPlayer;
        this.field = field;
        this.ball = ball;
        throw new PoobSoccerException("PoobSoccerException.NOT_DATA");
    }

    public Ball getBall(){
        return ball;
    }

    
}
