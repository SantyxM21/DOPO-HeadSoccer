package presentation;

import domain.DopoSoccer;
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
        this.backButton = CustomButton.createBackButton();
    }

    /*
    This method prepares the mode page panel
    @return the prepared mode page panel
    */
    public JPanel prepareModePage(){

        JLabel title = new JLabel("Modalidad");
        title.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 7));
        title.setForeground(new Color(247, 254, 255));                                 // azul marino, igual al popup
        title.setHorizontalAlignment(SwingConstants.CENTER);

        Component spacer = Box.createRigidArea(backButton.getPreferredSize());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 40, 0, GAME_SIZE.width / 40));
        topBar.add(spacer, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(CustomButton.keepSize(backButton), BorderLayout.EAST);

        // Botones de modalidad en el centro
        setTextImage(pvpButton, "/resources/pvp_text.png", "P vs P");
        setTextImage(pvmButton, "/resources/pvm_text.png", "P vs M");

        JPanel modes = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 8, 0));
        modes.setOpaque(false);
        modes.add(pvpButton);
        modes.add(pvmButton);

        JPanel center = new JPanel(new GridBagLayout());        // Centra el bloque de botones vertical y horizontalmente
        center.setOpaque(false);
        center.add(modes);

        // Nombre del juego abajo
        JLabel gameName = DopoSoccerGUI.createLogo(12);
        gameName.setBorder(BorderFactory.createEmptyBorder(0, 0, GAME_SIZE.height / 30, 0));

        this.add(topBar, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(gameName, BorderLayout.SOUTH);

        return this;
    }

    private void setTextImage(RoundButton button, String path, String fallback){
        Image image = DopoSoccerGUI.loadImage(path);
        if (image != null) {
            int width = (int)(button.getPreferredSize().width * 0.7);                
            button.setIcon(new ImageIcon(image.getScaledInstance(width, -1, Image.SCALE_SMOOTH)));
        } else {
            button.setText(fallback);
            button.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 22));
        }
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
                main.setPlayersMode(DopoSoccer.PVSP_MODE);
            }
        });
        pvmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Selection menu");
                main.setPlayersMode(DopoSoccer.PVSM_MODE);
            }
        });
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (DopoSoccerGUI.PANEL_BACKGROUND != null) g.drawImage(DopoSoccerGUI.PANEL_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
