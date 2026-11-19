package domain;

public class BlueAura extends Power {

    public BlueAura(){
        super("Aura Azul", 40);
    }

    @Override
    protected Effect createEffect(){
        return new PushEffect();
    }

    @Override 
    public Vector2D shotVelocity(int direction, Field field){
        // Sobreescribe el metodo ya que el balon sale con mayor velocidad que los otros poderes
        return new Vector2D(direction * field.scale(15), 0);    
    }

}
