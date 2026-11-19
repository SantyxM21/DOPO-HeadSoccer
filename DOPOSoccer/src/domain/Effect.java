package domain;

public abstract class Effect {
    protected double duration;
    protected double timeLeft;

    public Effect(double duration) {
        this.duration = duration;
        this.timeLeft = duration;
    }

    public void applyEffect(Player target, int direction, Field field) {
        timeLeft = duration;
    }

    //Cada frame
    public void update(Player target, double secondsElapsed) {
        if (timeLeft > 0) {
            timeLeft -= secondsElapsed;
            if (timeLeft < 0) timeLeft = 0;
        }
    }

    public void end(Player target) {
        target.restoreValues();
    }

    public boolean isFinished() {
        return timeLeft <= 0;
    }

    public double getDuration() {
        return duration;
    }

    public double getTimeLeft() {
        return timeLeft;
    }
}
