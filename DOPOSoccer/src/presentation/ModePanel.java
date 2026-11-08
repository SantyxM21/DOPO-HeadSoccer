package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ModePanel extends JPanel {

    private final Dimension GAME_SIZE;

    private RoundButton pvpButton;
    private RoundButton pvmButton;
    private CustomButton backButton;

    public ModePanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        this.pvpButton = new RoundButton();
        this.pvmButton = new RoundButton();
        this.backButton = new CustomButton("Volver");
    }

    /*
    This method prepares the mode page panel
    @return the prepared mode page panel
    */
    public JPanel prepareModePage(){

        JLabel title = new JLabel("Modalidad");
        title.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 9));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        Component spacer = Box.createRigidArea(backButton.getPreferredSize());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 40, 0, GAME_SIZE.width / 40));
        topBar.add(spacer, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(backButton, BorderLayout.EAST);

        // Botones de modalidad en el centro
        pvpButton.setText("P vs P");
        pvmButton.setText("P vs M");
        Font modeFont = new Font("Arial", Font.PLAIN, GAME_SIZE.height / 22);
        pvpButton.setFont(modeFont);
        pvmButton.setFont(modeFont);

        JPanel modes = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 8, 0));
        modes.setOpaque(false);
        modes.add(pvpButton);
        modes.add(pvmButton);

        JPanel center = new JPanel(new GridBagLayout());        // Centra el bloque de botones vertical y horizontalmente
        center.setOpaque(false);
        center.add(modes);

        // Nombre del juego abajo
        JLabel gameName = new JLabel("<html><center>DOPO<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 16));
        gameName.setHorizontalAlignment(SwingConstants.CENTER);
        gameName.setBorder(BorderFactory.createEmptyBorder(0, 0, GAME_SIZE.height / 30, 0));

        this.add(topBar, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(gameName, BorderLayout.SOUTH);

        return this;
    }

    public void prepareController(MainPanel main){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Home menu");            
            }
        });

        pvpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Selection menu");
            }
        });
        pvmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Selection menu");
            }
        });
    }
}
