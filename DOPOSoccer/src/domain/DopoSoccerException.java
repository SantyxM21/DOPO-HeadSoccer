package domain;

public class DopoSoccerException extends Exception {
    public static final String NOT_DATA = "Aun no hay datos";
    public static final String UNDER_CONSTRUCTION = "La funcionalidad esta bajo construcción";

    public DopoSoccerException(String ms){
        super(ms);
    }
}
