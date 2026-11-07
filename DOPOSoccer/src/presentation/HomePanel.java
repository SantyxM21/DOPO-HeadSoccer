package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class HomePanel extends JPanel{

    private final Dimension GAME_SIZE;

    private RoundButton timeButton;
    private RoundButton goalsButton;
    private RoundButton importGameButton;

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
        playButton = new CustomButton("Jugar");
        cancelButton = new CustomButton("Cancelar");


    }

    /* 
    This method prepares the home page panel 
    @return the prepared home page panel
    */
    public JPanel prepareHomePage(){

        JLabel gameName = new JLabel("<html><center>POOB<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 7));          // Tamaño de letra que se adapta
        gameName.setHorizontalAlignment(SwingConstants.CENTER);
        gameName.setBorder(BorderFactory.createEmptyBorder(0, 0, GAME_SIZE.height / 10, 0));   // margen inferior: sube el texto


        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 10, GAME_SIZE.height / 32));
        buttons.setOpaque(false);   
        buttons.add(createModeButton("Tiempo", timeButton));
        buttons.add(createModeButton("Goles", goalsButton));

        int side = GAME_SIZE.height / 6;                                                // más pequeño que los botones de modo
        importGameButton.setPreferredSize(new Dimension(side, side));
        importGameButton.setText("Abrir");
        importGameButton.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 30));

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, GAME_SIZE.width / 40, GAME_SIZE.height / 40));
        topBar.setOpaque(false);
        topBar.add(importGameButton);                                                   // esquina superior derecha

        this.add(topBar, BorderLayout.NORTH);
        this.add(gameName, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);

        return this;
    }

    public void prepareController(MainPanel main){
        timeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                popUpConfig("Tiempo");
            }
        });
        goalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                popUpConfig("Goles");
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                configDialog.dispose();             // Cierra el diálogo antes de cambiar de página
                main.showPage("Mode menu");
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
                    JOptionPane.showMessageDialog(null, "La funcionalidad se encuentra en construccion\n" +
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
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);         // se centra segun donde este la ventana no la panatalla
        dialog.setModal(true);

        JLabel message = new JLabel("ELEGIR " + ms.toUpperCase());
        message.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height/10));       
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        message.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 20, 0, 0, 0));   // margen superior

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
    @return the JPanel that contains the button and its label
    */
    private JPanel createModeButton(String text, RoundButton button){
        JPanel panel = new JPanel(new BorderLayout(0, 1));
        panel.setOpaque(false);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 35));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        panel.add(button, BorderLayout.CENTER);  // Se añade el botón redondo al panel en el centro
        panel.add(label, BorderLayout.SOUTH);               // Se añade la etiqueta debajo del botón
        return panel;
    }
}
