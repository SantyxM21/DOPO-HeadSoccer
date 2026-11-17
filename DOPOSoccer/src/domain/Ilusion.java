package domain;

public class Ilusion extends Power {

    public Ilusion(){
        super("Ilusión", 30);
    }

    @Override
    protected Effect createEffect(){
        return new ConfusionEffect();
    }
}
