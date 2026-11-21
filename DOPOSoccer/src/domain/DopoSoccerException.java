package domain;

public class DopoSoccerException extends Exception {
    public static final String INVALID_INPUT = "El número debe ser un entero postivo";
    public static final String NOT_DATA = "Aun no hay datos";
    public static final String UNDER_CONSTRUCTION = "La funcionalidad esta bajo construcción";
    public static final String INEXISTENT_CHARACTER = "El personaje no existe";
    public static final String INVALID_STAT = "La estadistica no existe";
    public static final String NOT_ENOUGH_POINTS = "No tienes puntos suficientes para esta mejora";
    public static final String MAX_LEVEL_REACHED = "La estadistica ya esta en su nivel maximo";
    public static final String INVALID_SITE = "No se selecciono un lado correcto";

    public static final String INVALID_GOALS = "Deben ser goles positivos";
    public static final String INVALID_TIME = "El tiempo debe ser positivo";
    public static final String INVALID_MACHINE = "No existe ese tipo de maquina";

    public static final String INVALID_DOPO_EXTENSION = "La extension del archivo debe ser .dopo";
    public static final String FILE_NOT_FOUND = "No se encuentra el archivo.";
    public static final String NOT_SERIALIZABLE = "No se permite guardar el archivo porque hay elementos invalidos.";
    public static final String ERROR_WHILE_WRITING = "Ocurrio un error mientras se escribia el archivo.";
    public static final String GENERAL_ERROR = "Se ha presentado un error general.";

    public DopoSoccerException(String ms){
        super(ms);
    }
}
