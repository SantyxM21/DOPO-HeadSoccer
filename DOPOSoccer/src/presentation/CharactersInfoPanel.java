package presentation;

import static domain.Stats.STATS_NAMES;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CharactersInfoPanel extends JPanel{
    
    private final Dimension GAME_SIZE;

    private CustomButton backButton;
    private JLabel playerPoints;

    // Personaje que muestra la tarjeta (posición en COUNTRIES)
    private String character;

    // Imagen y nombre de la tarjeta, se actualizan con showCharacter
    private final JLabel characterImage = new JLabel();
    private final JLabel characterName = new JLabel("", SwingConstants.CENTER);

    // Valor de cada estadística, en el mismo orden que Stats.STATS_NAMES
    private final JLabel[] statValues = new JLabel[STATS_NAMES.length];
    private final CustomButton[] plusBt = new CustomButton[STATS_NAMES.length];
    private CustomButton powerButton;

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
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 40, 0, GAME_SIZE.width / 40));


        JLabel gameName = DopoSoccerGUI.createLogo(20);

        playerPoints = new JLabel("Player : 0");
        playerPoints.setForeground(new Color(247, 254, 255)); 
        playerPoints.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 14));
        playerPoints.setHorizontalAlignment(SwingConstants.CENTER);

        // El logo y el boton de volver se ponen dentro de JPnales para darles el mismo tamaño
        // y que la información de los puntos quede bien centrada :)
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);
        left.add(gameName);
        JPanel right = CustomButton.keepSize(backButton);
        int sideWidth = Math.max(left.getPreferredSize().width, right.getPreferredSize().width);
        left.setPreferredSize(new Dimension(sideWidth, left.getPreferredSize().height));
        right.setPreferredSize(new Dimension(sideWidth, right.getPreferredSize().height));

        topBar.add(left, BorderLayout.WEST);
        topBar.add(playerPoints, BorderLayout.CENTER);
        topBar.add(right, BorderLayout.EAST);

        // Una sola tarjeta en el centro, con margen a los lados para que no se estire de mas
        JPanel body = new JPanel(new GridLayout(1, 1));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 20, GAME_SIZE.width / 3, GAME_SIZE.height / 20, GAME_SIZE.width / 3));
        body.add(characterCard(characterImage, characterName));

        this.add(topBar, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);

        return this;
    }
    
    public void prepareController(MainPanel main){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Selection menu");
            }
        });

        for (int i = 0; i < plusBt.length; i++) {
            final int stat = i;            //  Java no deja usar la variable i del ciclo dentro de la clase anónima
            plusBt[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    main.upgradeStat(selectedInfoPlayer, STATS_NAMES[stat]);  
                    main.updateInfoPlayer(selectedInfoPlayer, character);
                }
            });
        }

        powerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                MainPanel.funcUnderConstruction("En construccion");
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

        // Fila 2: una fila por estadística (Stats.STATS_NAMES), cada una con nombre, valor y botón "+"
        JPanel stats = new JPanel(new GridLayout(STATS_NAMES.length, 1));
        stats.setOpaque(false);
        Font statFont = new Font("Arial", Font.PLAIN, GAME_SIZE.height / 28);

        // Todos los nombres toman el ancho del más largo para que los valores queden alineados
        FontMetrics metrics = new JLabel().getFontMetrics(statFont);
        int labelWidth = 0;
        for (String statName : STATS_NAMES) labelWidth = Math.max(labelWidth, metrics.stringWidth(statName + ":"));
        labelWidth += GAME_SIZE.width / 80;

        for (int i = 0; i < STATS_NAMES.length; i++) {
            JLabel label = new JLabel(STATS_NAMES[i] + ":");
            label.setFont(statFont);
            JLabel value = new JLabel("0", SwingConstants.CENTER);     // el valor real lo pone updateInfo
            value.setFont(statFont);
            statValues[i] = value;
            CustomButton plus = new CustomButton("+");
            plus.setMargin(new Insets(0, 0, 0, 0));
            plus.setPreferredSize(new Dimension(GAME_SIZE.height / 24, GAME_SIZE.height / 24));
            plusBt[i] = plus;

            // El botón va en un FlowLayout para conservar su tamaño y no estirarse a la celda
            JPanel plusHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            plusHolder.setOpaque(false);
            plusHolder.add(plus);

            label.setPreferredSize(new Dimension(labelWidth, label.getPreferredSize().height));

            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.add(label, BorderLayout.WEST);
            row.add(value, BorderLayout.CENTER);
            row.add(plusHolder, BorderLayout.EAST);
            stats.add(row);
        }

        // Fila 3: boton de poder con su tamaño natural
        powerButton = new CustomButton("Poder");
        JPanel powerCentered = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));     // centra el botón sin estirarlo
        powerCentered.setOpaque(false);
        powerCentered.add(powerButton);

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
        characterName.setText(character);
        characterImage.setIcon(DopoSoccerGUI.flagIcon(character, GAME_SIZE.height / 5));
    }

    public void updateInfo(String name, int points, int[] stats, int card, String shownChar){ 
        
        this.selectedInfoPlayer = card;
        this.character = shownChar;
        playerPoints.setText(name+" : "+points);

        showCharacter();
        showStats(stats);
    }

    /*
    This method shows the character's stats in the card
    @param stats the values in the same order as Stats.STATS_NAMES
    */
    private void showStats(int[] stats){
        for (int i = 0; i < statValues.length && i < stats.length; i++) {
            statValues[i].setText(String.valueOf(stats[i]));
        }
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (DopoSoccerGUI.PANEL_BACKGROUND != null) g.drawImage(DopoSoccerGUI.PANEL_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
