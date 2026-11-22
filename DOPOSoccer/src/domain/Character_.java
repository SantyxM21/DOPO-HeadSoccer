package domain;

import java.io.Serializable;

public abstract class Character_ implements Serializable{
    protected String name; //el pais
    protected Power power; //el tiro unico de cada uno

    public Character_(String name, Power power){
        this.name = name;
        this.power = power;
    }

    public static Character_ create(String country) throws DopoSoccerException{
        if (country == null) throw new DopoSoccerException(DopoSoccerException.INEXISTENT_CHARACTER);
        switch (country) {
            case Corea.NAME:
                return new Corea();
            case Camerun.NAME:
                return new Camerun();
            case Nigeria.NAME:
                return new Nigeria();
            case USA.NAME:
                return new USA();
            default:
                throw new DopoSoccerException(DopoSoccerException.INEXISTENT_CHARACTER);
        }
    }

    public String getName(){
        return name;
    }

    public Power getPower(){
        return power;
    }
}
