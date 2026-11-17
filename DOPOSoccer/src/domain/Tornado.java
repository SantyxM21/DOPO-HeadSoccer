package domain;

public class Tornado extends Power{

    public Tornado(){
        super("Tornado", 40);
    }

    @Override
    protected Effect createEffect(){
        return new UnconsiousEffect();
    }
}
