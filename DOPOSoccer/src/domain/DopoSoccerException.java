package domain;

public class DopoSoccerException extends Exception {
    public static final String NOT_DATA = "Aun no hay datos";

    public DopoSoccerException(String ms){
        super(ms);
    }
}
