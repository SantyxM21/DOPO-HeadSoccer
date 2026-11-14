package presentation;

import java.awt.Image;
import java.util.*;
import java.net.URL;

import javax.swing.ImageIcon;


public class CharacterInfoView {
    private HashMap <String, Image> images = new HashMap<>();

    public CharacterInfoView(int height){

        addImage("Corea", "/resources/KR.png", height);
        addImage("Camerun", "/resources/CM.png", height);
        addImage("Nigeria", "/resources/NG.png", height);
        addImage("USA", "/resources/US.png", height);
    }

    private void addImage(String country, String path, int height){
        try {
            Image image = new ImageIcon(getClass().getResource(path)).getImage();
            images.put(country, image.getScaledInstance(-1, height, Image.SCALE_SMOOTH)); //Ancho -1 para ajustar en llamado
        } catch (Exception e) {
            System.err.println("La imagen de " + country + ": " + path + " no se pudo cargar");
        }
    }

    public Image getImage(String country){
        return images.get(country);
    }
}