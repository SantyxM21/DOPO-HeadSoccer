package domain;

public abstract class Character_ {
    protected String name; //el pais
    protected Power power; //el tiro unico de cada uno
     
    public Character_(String name, Power power){
        this.name = name;
        this.power = power;
    }

    public String getName(){
        return name;
    }

    public Power getPower(){
        return power;
    }
}
