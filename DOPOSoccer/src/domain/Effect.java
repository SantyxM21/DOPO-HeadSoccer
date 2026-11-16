package domain;

public abstract class Effect {
    protected double duration;
    protected double timeLeft;

    public Effect(double duration) {
        this.duration = duration;
        this.timeLeft = duration;
    }

    public void update(double secondsElapsed) {
        if (timeLeft > 0) {
            timeLeft -= secondsElapsed;
            if (timeLeft < 0) timeLeft = 0;
        }
    }

    public boolean isFinished() {
        return timeLeft <= 0;
    }

    public double getTimeLeft() {
        return timeLeft;
    }

    public void applyEffect(Player target, int direction) {
        timeLeft = duration;
    }

    //Cada frame
    public void tick(Player target) {}

    public boolean blockPlayer() {
        return false;
    }
}