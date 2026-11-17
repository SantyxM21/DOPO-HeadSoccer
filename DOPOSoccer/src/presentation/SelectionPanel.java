package presentation;

import domain.DopoSoccer;
import domain.Match;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectionPanel extends JPanel {

    private final Dimension GAME_SIZE;

    private CustomButton backButton;
    private CustomButton playButton;
    private CustomButton swapButton;

    private RoundButton leftCharacter;
    private RoundButton rightCharacter;
    private CustomButton leftPrevious;
    private CustomButton leftNext;
    private CustomButton rightPrevious;
    private CustomButton rightNext;
    private JLabel leftName;
    private JLabel rightName;

    private int leftOption = 0;
    private int rightOption = 0;


    public SelectionPanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;
        backButton = CustomButton.createBackButton();
        playButton = new CustomButton("Jugar");
        swapButton = new CustomButton("↔");
        leftCharacter = new RoundButton();
        rightCharacter = new RoundButton();
        leftPrevious = new CustomButton("←");      // ←
        leftNext = new CustomButton("→");          // →
        rightPrevious = new CustomButton("←");
        rightNext = new CustomButton("→");
        leftName = new JLabel("", SwingConstants.CENTER);
        rightName = new JLabel("", SwingConstants.CENTER);
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

        // Tres columnas: jugador izquierdo, centro (jugar) y jugador derecho / máquina
        JPanel body = new JPanel(new GridLayout(1, 3));
        body.setOpaque(false);
        body.add(createCharacterColumn(leftCharacter, DopoSoccer.COUNTRIES[leftOption], leftPrevious, leftNext, leftName));
        body.add(createCenterColumn());
        body.add(createCharacterColumn(rightCharacter, DopoSoccer.COUNTRIES[rightOption], rightPrevious, rightNext, rightName));

        this.add(topBar, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);

        return this;
    }

    public void prepareController(MainPanel main){
        showPlayerNames(main);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Mode menu");
            }
        });

        leftCharacter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.updateInfoPlayer(Match.LEFT_PLAYER, DopoSoccer.COUNTRIES[leftOption]);
                main.showPage("Character page");
            }
        });

        rightCharacter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.updateInfoPlayer(Match.RIGHT_PLAYER, DopoSoccer.COUNTRIES[rightOption]);
                main.showPage("Character page");
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main.startGame(DopoSoccer.COUNTRIES[leftOption], DopoSoccer.COUNTRIES[rightOption])) main.showPage("Game");
            }
        });

        leftNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(1, Match.LEFT_PLAYER);
            }
        });
        leftPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(-1, Match.LEFT_PLAYER);
            }
        });

        rightNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(1, Match.RIGHT_PLAYER);
            }
        });
        rightPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCharacter(-1, Match.RIGHT_PLAYER);
            }
        });

        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.swapProfiles();
                showPlayerNames(main);
            }
        });
    }

    private void showPlayerNames(MainPanel main){
        leftName.setText(main.getPlayerName(Match.LEFT_PLAYER));
        rightName.setText(main.getPlayerName(Match.RIGHT_PLAYER));
    }

    /*
    This method creates a column with a character circle, its arrows and the player name
    @param character the round button that shows the character
    @param characterName the text shown inside the circle
    @param previous the button to select the previous character
    @param next the button to select the next character
    @param name the label with the profile name shown below the arrows
    @return the panel that contains the whole column
    */
    private JPanel createCharacterColumn(RoundButton character, String characterName, CustomButton previous, CustomButton next, JLabel name){
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

    private void changeCharacter(int step, int side){
        int numCharacter = DopoSoccer.COUNTRIES.length;
        RoundButton character;
        String country;
        if (side == Match.LEFT_PLAYER) {
            leftOption = Math.floorMod(leftOption + step, numCharacter);   // floorMod evita indices negativos al ir hacia atras con previous
            character = leftCharacter;
            country = DopoSoccer.COUNTRIES[leftOption];
        } else {
            rightOption = Math.floorMod(rightOption + step, numCharacter);
            character = rightCharacter;
            country = DopoSoccer.COUNTRIES[rightOption];
        }
        DopoSoccerGUI.showFlag(character, country, GAME_SIZE.height / 8);
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (DopoSoccerGUI.PANEL_BACKGROUND != null) g.drawImage(DopoSoccerGUI.PANEL_BACKGROUND, 0, 0, getWidth(), getHeight(), this);
    }
}
