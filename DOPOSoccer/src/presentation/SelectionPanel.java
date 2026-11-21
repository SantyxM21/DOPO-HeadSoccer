package presentation;

import domain.DopoSoccer;
import domain.Machine;
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
    private JComboBox<String> machineProfile;      // reemplaza a rightName cuando el rival es una maquina
    private JPanel rightNameHolder;                 // muestra rightName o machineProfile segun la modalidad

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
        machineProfile = new JComboBox<>(Machine.PROFILE_NAMES);
        machineProfile.setSelectedIndex(Machine.EXPERT - 1);
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
        styleNameLabel(leftName);
        body.add(createCharacterColumn(leftCharacter, DopoSoccer.COUNTRIES[leftOption], leftPrevious, leftNext, leftName));
        body.add(createCenterColumn());
        body.add(createCharacterColumn(rightCharacter, DopoSoccer.COUNTRIES[rightOption], rightPrevious, rightNext, createRightNameHolder()));

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
                if (main.startGame(DopoSoccer.COUNTRIES[leftOption], DopoSoccer.COUNTRIES[rightOption], getMachineType())) main.showPage("Game");
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
    This method adapts the page to the chosen modality: against a machine the right
    player shows the machine profile selector and the profiles can not be swapped
    @param mode DopoSoccer.PVSP_MODE or DopoSoccer.PVSM_MODE
    */
    public void setPlayersMode(int mode){
        boolean vsMachine = mode == DopoSoccer.PVSM_MODE;
        ((CardLayout) rightNameHolder.getLayout()).show(rightNameHolder, vsMachine ? MACHINE_CARD : HUMAN_CARD);
        swapButton.setEnabled(!vsMachine);
        swapButton.setCursor(new Cursor(vsMachine ? Cursor.DEFAULT_CURSOR : Cursor.HAND_CURSOR));
    }

    /*
    This method returns the machine profile chosen in the selector
    @return Machine.DUMB, Machine.OFENSIVE, Machine.DEFENSIVE or Machine.EXPERT
    */
    public int getMachineType(){
        return machineProfile.getSelectedIndex() + 1;       // PROFILE_NAMES esta en el mismo orden que los tipos
    }

    private static final String HUMAN_CARD = "human";
    private static final String MACHINE_CARD = "machine";

    /*
    This method creates the area below the right arrows, that shows either
    the profile name (vs player) or the machine profile selector (vs machine)
    @return the panel that switches between both
    */
    private JPanel createRightNameHolder(){
        styleNameLabel(rightName);

        machineProfile.setFont(rightName.getFont());
        machineProfile.setBackground(Color.WHITE);
        machineProfile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        machineProfile.setFocusable(false);
        machineProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        machineProfile.setToolTipText("Perfil de la máquina");
        if (machineProfile.getRenderer() instanceof JLabel) ((JLabel) machineProfile.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        int labelHeight = rightName.getFontMetrics(rightName.getFont()).getHeight() + 6;       // casi la misma altura que la etiqueta, sin recortar el texto del combo
        machineProfile.setPreferredSize(new Dimension(machineProfile.getPreferredSize().width + GAME_SIZE.width / 30, labelHeight));

        rightNameHolder = new JPanel(new CardLayout());
        rightNameHolder.setOpaque(false);
        rightNameHolder.add(keepCentered(rightName), HUMAN_CARD);
        rightNameHolder.add(keepCentered(machineProfile), MACHINE_CARD);
        return rightNameHolder;
    }

    // Envuelve el componente para que CardLayout no lo estire y se vea con su tamaño natural
    private JPanel keepCentered(JComponent component){
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(component);
        return wrapper;
    }

    private void styleNameLabel(JLabel name){
        name.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 35));
        name.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(0, GAME_SIZE.width / 30, 0, GAME_SIZE.width / 30)));
        name.setOpaque(true);
        name.setBackground(Color.WHITE);
    }

    /*
    This method creates a column with a character circle, its arrows and the player name
    @param character the round button that shows the character
    @param characterName the text shown inside the circle
    @param previous the button to select the previous character
    @param next the button to select the next character
    @param name the component shown below the arrows (profile name or machine selector)
    @return the panel that contains the whole column
    */
    private JPanel createCharacterColumn(RoundButton character, String characterName, CustomButton previous, CustomButton next, JComponent name){
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
