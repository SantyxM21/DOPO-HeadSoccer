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
        Image image = DopoSoccerGUI.loadImage(path);
        if (image != null) images.put(country, image.getScaledInstance(-1, height, Image.SCALE_SMOOTH)); //Ancho -1 para ajustar en llamado
    }

    public Image getImage(String country){
        return images.get(country);
    }
}