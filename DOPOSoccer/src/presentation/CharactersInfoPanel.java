package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CharactersInfoPanel extends JPanel{
    
    private final Dimension GAME_SIZE;

    private CustomButton backButton;
    private CustomButton previous;
    private CustomButton next;

    public CharactersInfoPanel(){
        super(new BorderLayout());
        this.GAME_SIZE = MainPanel.GAME_SIZE;

        backButton = CustomButton.createBackButton();
        previous = new CustomButton("←");
        next = new CustomButton("→");
        
    }

    /*   */
    public JPanel prepareCharacterPage(){
        
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 30, GAME_SIZE.width / 20, 0, GAME_SIZE.width / 40));


        JLabel gameName = new JLabel("<html><center>DOPO<br>SOCCER</center></html>");
        gameName.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 20));
        gameName.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel playerPoints = new JLabel("Player1 :" + "1000 pts");
        playerPoints.setFont(new Font("Arial", Font.BOLD, GAME_SIZE.height / 15));
        playerPoints.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(gameName, BorderLayout.WEST);
        topBar.add(playerPoints, BorderLayout.CENTER);
        topBar.add(CustomButton.keepSize(backButton), BorderLayout.EAST);

        // Dos columnas iguales, una por tarjeta, separadas por un espacio
        JPanel body = new JPanel(new GridLayout(1, 2, GAME_SIZE.width / 20, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(GAME_SIZE.height / 20, 0, GAME_SIZE.height / 20, 0));
        body.add(characterCard("Corea"));
        body.add(characterCard("USA"));

        this.add(topBar, BorderLayout.NORTH);
        this.add(centerVertically(previous), BorderLayout.WEST);
        this.add(body, BorderLayout.CENTER);
        this.add(centerVertically(next), BorderLayout.EAST);
        return this;
    }
    
    /*   */
    public void prepareController(MainPanel main){
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                main.showPage("Selection menu");
            }
        });
        
    }

    /*
    This method wraps a component so BorderLayout does not stretch it, leaving margin on the sides
    @param component the component to wrap
    @return a transparent panel that keeps the component's preferred size
    */
    private JPanel centerVertically(JComponent component){
        // FlowLayout respeta el tamaño preferido del botón y deja margen a los lados
        JPanel holder = new JPanel(new FlowLayout(FlowLayout.CENTER, GAME_SIZE.width / 18, 0));
        holder.setOpaque(false);
        holder.add(component);

        // 9 filas iguales con el botón en la del medio: así queda centrado sin importar el tamaño de la ventana
        int rows = 9;
        JPanel wrapper = new JPanel(new GridLayout(rows, 1));
        wrapper.setOpaque(false);
        for (int i = 0; i < rows; i++) {
            if (i == rows / 2) wrapper.add(holder);
            else wrapper.add(new JLabel());                  // fila vacía
        }
        return wrapper;
    }

    /*
    This method creates the card of a character with its image, name, stats each with a "+" button and power button
    @param name the name of the character
    @return the panel that contains the whole card
    */
    private JPanel characterCard(String name){
        // la tarjeta se divide en 3 filas iguales: imagen con nombre, estadísticas y boton de poder
        JPanel card = new JPanel(new GridLayout(3, 1, 0, GAME_SIZE.height / 60));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(GAME_SIZE.height / 40, GAME_SIZE.width / 40, GAME_SIZE.height / 40, GAME_SIZE.width / 40)));

        // Fila 1: espacio de la imagen 
        JPanel image = new JPanel();
        image.setBackground(Color.WHITE);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel imageHolder = new JPanel(new GridLayout(1, 1));
        imageHolder.setOpaque(false);
        imageHolder.setBorder(BorderFactory.createEmptyBorder(0, GAME_SIZE.width / 14, 0, GAME_SIZE.width / 14));
        imageHolder.add(image);

        // Nombre pegado debajo de la imagen
        JLabel characterName = new JLabel(name, SwingConstants.CENTER);
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
}
