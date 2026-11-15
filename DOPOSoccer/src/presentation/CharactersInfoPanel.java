package presentation;

import static domain.DopoSoccer.COUNTRIES;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CharactersInfoPanel extends JPanel{
    
    private final Dimension GAME_SIZE;

    private CustomButton backButton;

    // Personaje que muestra la tarjeta (posición en COUNTRIES)
    private int character = 0;

    // Imagen y nombre de la tarjeta, se actualizan con showCharacter
    private final JLabel characterImage = new JLabel();
    private final JLabel characterName = new JLabel("", SwingConstants.CENTER);

    private int selectedInfoPlayer; //1 left 2 right


    public CharactersInfoPanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;

        backButton = CustomButton.createBackButton();
    }

    /*   */
    public JPanel prepareCharacterPage(){
        
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 20, 0, GAME_SIZE.width / 40));


        JLabel gameName = new JLabel("<html><center>DOPO<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 20));
        gameName.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel playerPoints = new JLabel("Player :" + "1000 pts");
        playerPoints.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 15));
        playerPoints.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(gameName, BorderLayout.WEST);
        topBar.add(playerPoints, BorderLayout.CENTER);
        topBar.add(CustomButton.keepSize(backButton), BorderLayout.EAST);

        // Una sola tarjeta en el centro, con margen a los lados para que no se estire de mas
        JPanel body = new JPanel(new GridLayout(1, 1));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 20, GAME_SIZE.width / 3, GAME_SIZE.height / 20, GAME_SIZE.width / 3));
        body.add(characterCard(characterImage, characterName));

        this.add(topBar, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);

        showCharacter();
        return this;
    }
    
    public void prepareController(MainPanel main){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Selection menu");
            }
        });
    }

    /*
    This method creates the card of a character with its image, name, stats each with a "+" button and power button
    @param image the label where the character's flag is shown
    @param characterName the label where the character's name is shown
    @return the panel that contains the whole card
    */
    private JPanel characterCard(JLabel image, JLabel characterName){
        // la tarjeta se divide en 3 filas iguales: imagen con nombre, estadísticas y boton de poder
        JPanel card = new JPanel(new GridLayout(3, 1, 0, GAME_SIZE.height / 60));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(GAME_SIZE.height / 40, GAME_SIZE.width / 40, GAME_SIZE.height / 40, GAME_SIZE.width / 40)));

        // Fila 1: espacio de la imagen 
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setVerticalAlignment(SwingConstants.CENTER);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel imageHolder = new JPanel(new GridLayout(1, 1)); //Recuadro que va a contener la imagen
        imageHolder.setOpaque(false);
        imageHolder.setBorder(BorderFactory.createEmptyBorder(0, GAME_SIZE.width / 14, 0, GAME_SIZE.width / 14));
        imageHolder.add(image);
        
        

        // Nombre pegado debajo de la imagen
        characterName.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 22));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(imageHolder, BorderLayout.CENTER);
        header.add(characterName, BorderLayout.SOUTH);

        // Fila 2: cuatro estadísticas, cada una con nombre, valor y botón "+"
        String[] statNames = {"Velocidad:", "Salto:", "Patada:", "Vida:"};
        JPanel stats = new JPanel(new GridLayout(statNames.length, 1));
        stats.setOpaque(false);
        Font statFont = new Font("Arial", Font.PLAIN, GAME_SIZE.height / 28);
        int labelWidth = new JLabel().getFontMetrics(statFont).stringWidth("Velocidad:") + GAME_SIZE.width / 80;   // ancho del nombre más largo
        for (String statName : statNames) {
            JLabel label = new JLabel(statName);
            label.setFont(statFont);
            JLabel value = new JLabel("1", SwingConstants.CENTER);
            value.setFont(statFont);
            CustomButton plus = new CustomButton("+");
            plus.setMargin(new Insets(0, 0, 0, 0));
            plus.setPreferredSize(new Dimension(GAME_SIZE.height / 24, GAME_SIZE.height / 24));

            // El botón va en un FlowLayout para conservar su tamaño y no estirarse a la celda
            JPanel plusHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            plusHolder.setOpaque(false);
            plusHolder.add(plus);

            // Todos los nombres toman el valor del string que sea mas largo
            label.setPreferredSize(new Dimension(labelWidth, label.getPreferredSize().height));

            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.add(label, BorderLayout.WEST);
            row.add(value, BorderLayout.CENTER);
            row.add(plusHolder, BorderLayout.EAST);
            stats.add(row);
        }

        // Fila 3: boton de poder con su tamaño natural
        CustomButton power = new CustomButton("Poder");
        JPanel powerCentered = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));     // centra el botón sin estirarlo
        powerCentered.setOpaque(false);
        powerCentered.add(power);

        JPanel powerHolder = new JPanel(new BorderLayout());     // SOUTH pega el botón al fondo de la fila (y de la tarjeta)
        powerHolder.setOpaque(false);
        powerHolder.add(powerCentered, BorderLayout.SOUTH);

        card.add(header);
        card.add(stats);
        card.add(powerHolder);
        return card;
    }

    /* This method shows the current character's flag and name in the card */
    private void showCharacter(){
        String country = COUNTRIES[character];
        characterName.setText(country);
        characterImage.setIcon(DopoSoccerGUI.flagIcon(country, GAME_SIZE.height / 6));
    }

    public void setSelectedInfoPlayer(int card){ 
        this.selectedInfoPlayer = card;
    }
}
