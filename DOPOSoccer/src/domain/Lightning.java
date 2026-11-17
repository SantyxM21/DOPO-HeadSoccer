package domain;

public class Lightning extends Power {

    public Lightning(){
        super("Rayo", 60);
    }

    @Override
    protected Effect createEffect(){
        return new ElectroEffect();
    }
}
