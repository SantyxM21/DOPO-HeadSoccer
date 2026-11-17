package domain;

public class BlueAura extends Power {

    public BlueAura(){
        super("Aura Azul", 40);
    }

    @Override
    protected Effect createEffect(){
        return new PushEffect();
    }
}
