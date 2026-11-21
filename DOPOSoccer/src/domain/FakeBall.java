package domain;

// Balon falso de un tiro como Ilusion
// vuela igual que el real, pero Match nunca lo usa
// para choques con los jugadores ni para goles. 
// Desaparece cuando se acaba su tiempo.
public class FakeBall extends Ball {
    private double timeLeft;

    public FakeBall(Field field, Power power, Player shooter, int direction, Vector2D velocity, double duration){
        super(field);
        chargePower(power, shooter, direction);     // sale del mismo punto y con la misma fisica que el real
        setVelocity(velocity);                      // cada falso con su propio angulo
        this.timeLeft = duration;
    }

    public void update(double seconds){
        timeLeft -= seconds;
    }

    public boolean isFinished(){
        return timeLeft <= 0;
    }

    public double getTimeLeft(){
        return timeLeft;
    }
}
