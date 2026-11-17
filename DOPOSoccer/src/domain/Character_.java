package domain;

public abstract class Character_ {
    protected String name; //el pais
    protected Power power; //el tiro unico de cada uno
     
    public Character_(String name, Power power){
        this.name = name;
        this.power = power;
    }

    public static Character_ create(String country) throws DopoSoccerException{
        switch (country) {
            case "Corea del Sur":
                return new Corea();
            case "Camerún":
                return new Camerun();
            case "Nigeria":
                return new Nigeria();
            case "Estados Unidos":
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
