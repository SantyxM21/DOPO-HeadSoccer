package presentation;

import java.awt.*;
import javax.swing.*;


/**
 * Write a description of class PoobSoccerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PoobSoccerGUI extends JFrame{

    private final MainPanel main = new MainPanel();

    public PoobSoccerGUI(){
        this.add(main);                                     //Se añade el panel al frame
        this.setResizable(false);                           //No permite modificar el tamaño de la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setBackground(Color.BLACK);
        this.pack();                                        // Toma las dimensiones del panel que se añadio 
        // this.setIconImage(img);
        this.setLocationRelativeTo(null);                   //Centra la ventana
        this.setVisible(true);  //Inicio h
    }

    public static void main(String[] args) {
        PoobSoccerGUI gui = new PoobSoccerGUI();
    }
}
