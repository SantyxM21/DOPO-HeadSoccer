package domain;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 */
public class Log{
    public static String name="DopoSoccer";

    public static void record(Exception e){
        try{
            Logger logger=Logger.getLogger(name);
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(name+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            logger.removeHandler(file);         // evita que se acumulen handlers en cada llamada
            file.close();
        }catch (Exception oe){
            oe.printStackTrace();               // si el log falla, el juego sigue funcionando
        }
    }
}


