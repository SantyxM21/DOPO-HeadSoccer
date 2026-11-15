package domain;

public class DopoSoccerException extends Exception {
    public static final String NOT_DATA = "Aun no hay datos";
    public static final String UNDER_CONSTRUCTION = "La funcionalidad esta bajo construcción";
    public static final String INEXISTENT_CHARACTER = "El personaje no existe";
    public static final String INVALIDAD_STAT = "La estadistica no existe";
    public static final String INVALID_SITE = "No se selecciono un lado correcto";

    public static final String INVALID_GOALS = "Deben ser goles positivos";
    public static final String INVALID_TIME = "El tiempo debe ser positivo";

    public DopoSoccerException(String ms){
        super(ms);
    }
}
