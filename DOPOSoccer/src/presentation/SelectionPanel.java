package presentation;

import domain.DopoSoccer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectionPanel extends JPanel {

    private final Dimension GAME_SIZE;

    private CustomButton backButton;
    private CustomButton playButton;
    private CustomButton swapButton;

    private RoundButton player1Character;
    private RoundButton player2Character;
    private CustomButton player1Previous;
    private CustomButton player1Next;
    private CustomButton player2Previous;
    private CustomButton player2Next;

    private int optionP1 = 0;
    private int optionP2 = 0;


    public SelectionPanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        backButton = CustomButton.createBackButton();
        playButton = new CustomButton("Jugar");
        swapButton = new CustomButton("↔");           // ↔ intercambia los personajes
        player1Character = new RoundButton();
        player2Character = new RoundButton();
        player1Previous = new CustomButton("←");      // ←
        player1Next = new CustomButton("→");          // →
        player2Previous = new CustomButton("←");
        player2Next = new CustomButton("→");

    }

    /*
    This method prepares the character selection page panel
    @return the prepared selection page panel
    */
    public JPanel prepareSelectionPage(){

        JLabel title = new JLabel("SELECCIÓN PERSONAJE");
        title.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 11));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(new Color(247, 254, 255));   
        

        Component spacer = Box.createRigidArea(backButton.getPreferredSize());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 40, 0, GAME_SIZE.width / 40));
        topBar.add(spacer, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(CustomButton.keepSize(backButton), BorderLayout.EAST);

        // Tres columnas: jugador 1, centro (jugar) y jugador 2 / máquina
        JPanel body = new JPanel(new GridLayout(1, 3));
        body.setOpaque(false);
        body.add(createCharacterColumn(player1Character, DopoSoccer.COUNTRIES[optionP1], player1Previous, player1Next, "Player 1"));
        body.add(createCenterColumn());
        body.add(createCharacterColumn(player2Character, DopoSoccer.COUNTRIES[optionP2], player2Previous, player2Next, "Machine"));

        this.add(topBar, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);

        return this;
    }

    public void prepareController(MainPanel main){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Mode menu");
            }
        });

        player1Character.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.updateInfoPlayer(1, DopoSoccer.COUNTRIES[optionP1]);
                main.showPage("Character page");
            }
        });

        player2Character.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.updateInfoPlayer(2, DopoSoccer.COUNTRIES[optionP2]);
                main.showPage("Character page");
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.startGame("Corea", "Nigeria")) main.showPage("Game");
            }
        });

        player1Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(1, 1);
            }
        });
        player1Previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(-1, 1);
            }
        });

        player2Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(1, 2);
            }
        });
        player2Previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(-1, 2);
            }
        });
        // TODO:swapButton y las flechas de cada jugador
    }

    /*
    This method creates a column with a character circle, its arrows and the player name
    @param character the round button that shows the character
    @param characterName the text shown inside the circle
    @param previous the button to select the previous character
    @param next the button to select the next character
    @param playerName the text shown below the arrows
    @return the panel that contains the whole column
    */
    private JPanel createCharacterColumn(RoundButton character, String characterName, CustomButton previous, CustomButton next, String playerName){
        int side = GAME_SIZE.height / 4;
        Dimension fixed = new Dimension(side, side);      // diámetro fijo del círculo
        character.setPreferredSize(fixed);
        character.setMinimumSize(fixed);
        character.setMaximumSize(fixed);

        DopoSoccerGUI.showFlag(character, characterName, GAME_SIZE.height / 8);
        character.setFont(new Font("Arial", Font.PLAIN, GAME_SIZE.height / 30));

        JPanel arrows = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 40, 0));
        arrows.setOpaque(false);
        arrows.add(previous);
        arrows.add(next);

        JLabel name = new JLabel(playerName, SwingConstants.CENTER);
        name.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 35));
        name.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, GAME_SIZE.width / 30, 0, GAME_SIZE.width / 30)));
        name.setOpaque(true);
        name.setBackground(Color.WHITE);

        return stackCentered(GAME_SIZE.height / 40, character, arrows, name);
    }

    /*
    This method creates the center column with the game name, the play button and the swap button
    @return the panel that contains the center column
    */
    private JPanel createCenterColumn(){
        JLabel gameName = DopoSoccerGUI.createLogo(14);
        return stackCentered(GAME_SIZE.height / 25, gameName, playButton, swapButton);
    }

    /*
    This method stacks the given components in a column, centered, with a gap between them
    @param gap the vertical space between components
    @param components the components to stack
    @return the panel that contains the stacked components
    */
    private JPanel stackCentered(int gap, JComponent... components){  // permite pasar cuantos componentes uno quiera, separados por comas como una especie de arreglo
        JPanel column = new JPanel(new GridBagLayout());       // GridBagLayout centra el bloque en la celda
        column.setOpaque(false);

        JPanel stack = new JPanel();
        stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));    // cada fila mantiene su altura natural, pero se ajusta en ancho
        stack.setOpaque(false);
        for (int i = 0; i < components.length; i++) {
            if (i > 0) stack.add(Box.createVerticalStrut(gap));     // añade un componente invisible que sirve como espaciador, por eso el "gap"
            components[i].setAlignmentX(Component.CENTER_ALIGNMENT);    
            stack.add(components[i]);
        }

        column.add(stack);
        return column;
    }

    private void changeCharacter(int step, int player){
        int numCharacter = DopoSoccer.COUNTRIES.length;
        RoundButton character;
        String country;
        if (player == 1) {
            optionP1 = Math.floorMod(optionP1 + step, numCharacter);   // floorMod evita indices negativos al ir hacia atras con previous
            character = player1Character;
            country = DopoSoccer.COUNTRIES[optionP1];
        } else {
            optionP2 = Math.floorMod(optionP2 + step, numCharacter);
            character = player2Character;
            country = DopoSoccer.COUNTRIES[optionP2];
        }
        DopoSoccerGUI.showFlag(character, country, GAME_SIZE.height / 8);
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (DopoSoccerGUI.GAME_BACKGROUND != null) g.drawImage(DopoSoccerGUI.GAME_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
