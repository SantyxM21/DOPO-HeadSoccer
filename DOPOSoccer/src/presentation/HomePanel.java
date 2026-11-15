package presentation;

import domain.DopoSoccer;
import domain.DopoSoccerException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class HomePanel extends JPanel{

    private final Dimension GAME_SIZE;

    private RoundButton timeButton;
    private RoundButton goalsButton;
    private RoundButton importGameButton;
    private CustomButton backButton;
    private final Image configBackground = DopoSoccerGUI.loadImage("/resources/fondo_popup.png");

    private JDialog configDialog;
    private JTextField configSizeInput;
    private CustomButton playButton;
    private CustomButton cancelButton;

    public HomePanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        timeButton = new RoundButton();
        goalsButton = new RoundButton();
        importGameButton = new RoundButton();
        backButton = CustomButton.createBackButton();
        playButton = new CustomButton("Jugar");
        cancelButton = new CustomButton("Cancelar");


    }

    /* 
    This method prepares the home page panel 
    @return the prepared home page panel
    */
    public JPanel prepareHomePage(){

        JLabel gameName = DopoSoccerGUI.createLogo(6);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 10, GAME_SIZE.height / 32));
        buttons.setOpaque(false);   
        buttons.add(createModeButton("Tiempo", timeButton, "/resources/clock.png"));
        buttons.add(createModeButton("Goles", goalsButton, "/resources/ball.png"));

        int side = GAME_SIZE.height / 6;                                                // más pequeño que los botones de modo
        importGameButton.setPreferredSize(new Dimension(side, side));
        Image openIcon = DopoSoccerGUI.loadImage("/resources/open_file.png");
        if (openIcon != null) {
            int iconSide = (int)(side * 0.6);                                          // el icono cabe dentro del círculo
            importGameButton.setIcon(new ImageIcon(openIcon.getScaledInstance(iconSide, iconSide, Image.SCALE_SMOOTH)));
        } else {
            importGameButton.setText("Abrir");                                         // si no carga el icono, se muestra el texto
            importGameButton.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 30));
        }
        importGameButton.setToolTipText("Abrir partida");

        JPanel importWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));      // evita que BorderLayout lo estire
        importWrapper.setOpaque(false);
        importWrapper.add(importGameButton);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 40, 0, GAME_SIZE.width / 40));
        topBar.add(importWrapper, BorderLayout.WEST);                                  // esquina superior izquierda
        topBar.add(CustomButton.keepSize(backButton), BorderLayout.EAST);              // esquina superior derecha

        this.add(topBar, BorderLayout.NORTH);
        this.add(gameName, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);

        return this;
    }

    public void prepareController(MainPanel main){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Loading Page");
            }
        });

        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                popUpConfig("Tiempo");
                main.setMatchMode(DopoSoccer.TIME_MODE);
            }
        });
        goalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                popUpConfig("Goles");
                main.setMatchMode(DopoSoccer.GOALS_MODE);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    int inputCondition = checkPositiveNum(configSizeInput.getText().trim());
                    configDialog.dispose();             // Cierra el diálogo antes de cambiar de página
                    main.showPage("Mode menu");
                    main.setConfigMode(inputCondition);
                } catch (DopoSoccerException ex) {
                    JOptionPane.showMessageDialog(configDialog, ex.getMessage(), "Valor no válido ", JOptionPane.ERROR_MESSAGE);
                    configSizeInput.requestFocusInWindow();
                    configSizeInput.selectAll();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                configDialog.dispose();             
            }
        });

        importGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser selector = new JFileChooser();
                
                int result = selector.showSaveDialog(null);

                //0 indica que selecciono guardar/abrir
                if (result == 0){
                    File fileToSave = selector.getSelectedFile();
                    MainPanel.funcUnderConstruction("La funcionalidad se encuentra en construccion\n" +
                        "Nombre del archivo: " + fileToSave.getName());
                }           
            }
        });
    }

    private void popUpConfig(String ms){
        JDialog dialog = new JDialog();
        configDialog = dialog;                      
        dialog.setTitle("Configuración de juego");
        dialog.setSize((int)(GAME_SIZE.width/1.3), (int)(GAME_SIZE.height/1.5));
        dialog.setContentPane(new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (configBackground != null) g.drawImage(configBackground, 0, 0, getWidth(), getHeight(), this);
            }
        });
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);         // se centra segun donde este la ventana no la panatalla
        dialog.setModal(true);

        JLabel message = new JLabel("ELEGIR " + ms.toUpperCase());
        message.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 10));
        message.setForeground(new Color(27, 42, 85));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        message.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 20, 0, 0, 0));
        int inputFont = GAME_SIZE.height / 16;
        JLabel inputLabel = new JLabel(ms + " :");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, inputFont));

        configSizeInput = new JTextField("2", 4);
        configSizeInput.setFont(new Font("Arial", Font.PLAIN, inputFont));
        configSizeInput.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 40, GAME_SIZE.width / 15));
        form.setOpaque(false);
        form.add(inputLabel);
        form.add(configSizeInput);

        JPanel dialogOptions = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 10, GAME_SIZE.width / 40)); 
        dialogOptions.setOpaque(false);
        
        dialogOptions.add(playButton);
        dialogOptions.add(cancelButton);


        dialog.add(message, BorderLayout.NORTH);
        dialog.add(form, BorderLayout.CENTER);
        dialog.add(dialogOptions, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    /* 
    This method creates a mode button with the given text 
    @param text the text for the button
    @param button the button to place in the panel (so it can be used later in the controller)
    @param imagePath the path of the image to show inside the button (null for no image)
    @return the JPanel that contains the button and its label
    */
    private JPanel createModeButton(String text, RoundButton button, String imagePath){
        JPanel panel = new JPanel(new BorderLayout(0, 1));
        panel.setOpaque(false);

        Image image = (imagePath != null) ? DopoSoccerGUI.loadImage(imagePath) : null;
        if (image != null) {
            int side = (int)(button.getPreferredSize().height * 0.6);             // la imagen cabe dentro del círculo
            button.setIcon(new ImageIcon(image.getScaledInstance(side, side, Image.SCALE_SMOOTH)));
        }

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 35));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        panel.add(button, BorderLayout.CENTER);  // Se añade el botón redondo al panel en el centro
        panel.add(label, BorderLayout.SOUTH);               // Se añade la etiqueta debajo del botón
        return panel;
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (DopoSoccerGUI.PANEL_BACKGROUND != null) g.drawImage(DopoSoccerGUI.PANEL_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }

    private int checkPositiveNum(String text) throws DopoSoccerException{
        try {
            int num = Integer.parseInt(text.trim());
            if(num <= 0){
                throw new DopoSoccerException(DopoSoccerException.INVALID_INPUT +" "+ text);
            }
            return num;
        } catch (NumberFormatException e) {
            throw new DopoSoccerException(DopoSoccerException.INVALID_INPUT +" "+ text);
        }
    }
}
