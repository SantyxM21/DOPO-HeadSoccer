# Cheat Sheet - Capa de presentación

Lo que más se repite en `DOPOSoccer/src/presentation`.

## Layouts

```java
new BorderLayout()                    divide en 5 zonas: NORTH, SOUTH, EAST, WEST y CENTER
panel.add(x, BorderLayout.NORTH)      pone x arriba (NORTH respeta el alto y estira el ancho)
panel.add(x, BorderLayout.CENTER)     el centro se queda con todo el espacio que sobra
new BorderLayout(0, 2)                lo mismo pero con 2px de separación vertical entre zonas

new FlowLayout()                      pone los componentes en fila, uno al lado del otro
new FlowLayout(FlowLayout.CENTER, hgap, vgap)   fila centrada con separación horizontal/vertical
                                      ojo: si no caben, los pasa a la siguiente línea

new GridLayout(filas, columnas)       cuadrícula donde todas las celdas miden lo mismo
new GridLayout(1, 3, gap, 0)          3 columnas iguales (así está el marcador: J1 / centro / J2)
new GridLayout(5, 1, 0, gap)          5 filas iguales, una debajo de la otra

new GridBagLayout()                   el más flexible; aquí se usa solo para centrar algo en el medio
new BoxLayout(panel, BoxLayout.Y_AXIS)    apila en columna respetando la altura de cada uno
new CardLayout()                      varias "páginas" en el mismo lugar, se ve una a la vez
setLayout(null)                       sin layout: cada cosa se ubica a mano con setBounds(x, y, w, h)
```

## Paneles y estilo

```java
new JPanel(new BorderLayout())        crea un panel ya con su layout
.setOpaque(false)                     sirve para quitar el fondo gris por defecto (se ve lo de atrás)
.setBackground(Color.WHITE)           color de fondo (solo se ve si es opaco)
.setForeground(Color.WHITE)           color del texto
.setFont(new Font("Arial", Font.BOLD, 20))   fuente, estilo (BOLD / PLAIN) y tamaño
new Color(27, 42, 85)                 color en RGB
.setText("...")                       cambia el texto de un JLabel o botón
.setHorizontalAlignment(SwingConstants.CENTER)   centra el texto dentro del label
new JLabel("texto", SwingConstants.CENTER)       label con el texto ya centrado
.setVisible(true)                     muestra el componente o la ventana
```

## Tamaños y bordes

```java
.setPreferredSize(new Dimension(w, h))     tamaño que "le gustaría" tener (el layout puede ignorarlo)
.getPreferredSize().height                 alto que pide el componente según su contenido
.setSize(w, h)                             tamaño fijo (se usa en ventanas y diálogos)
.setBorder(BorderFactory.createEmptyBorder(arriba, izq, abajo, der))   margen invisible (padding)
BorderFactory.createLineBorder(Color.BLACK, 2)   borde visible de 2px
BorderFactory.createCompoundBorder(afuera, adentro)   junta dos bordes (ej: línea + margen)
.setMargin(new Insets(10, 30, 10, 30))     espacio entre el texto y el borde de un botón
GAME_SIZE.height / 10                      todo se calcula según el tamaño de la ventana,
                                           así se adapta a las 3 resoluciones
```

## Botones (CustomButton / RoundButton)

```java
.addActionListener(new ActionListener() {...})   qué hace el botón al darle clic
.setContentAreaFilled(false)          no pinta el fondo por defecto del botón (lo pintamos nosotros)
.setBorderPainted(false)              quita el borde por defecto
.setFocusPainted(false)               quita el recuadro que sale al hacer clic
.setFocusable(false)                  el botón no roba el foco (si no, el teclado deja de mover a los jugadores)
.setCursor(new Cursor(Cursor.HAND_CURSOR))   cursor de manito al pasar por encima
.setIcon(new ImageIcon(img))          pone una imagen dentro del botón o label
```

## Imágenes y dibujo

```java
DopoSoccerGUI.loadImage("/resources/x.png")    carga una imagen de resources (null si falla)
img.getScaledInstance(w, h, Image.SCALE_SMOOTH)   la reescala; con w = -1 mantiene la proporción
DopoSoccerGUI.flagIcon(pais, alto)             bandera del país lista para un JLabel

protected void paintComponent(Graphics g)      se sobreescribe para dibujar a mano (ej: fondo del panel)
super.paintComponent(g)                        siempre primero, limpia lo que había antes
g.drawImage(img, 0, 0, getWidth(), getHeight(), this)   dibuja la imagen estirada a todo el panel
g.setColor(Color.ORANGE)                       color del "pincel"
g.fillOval(x, y, w, h)                         círculo relleno (así se dibuja el balón)
g.drawRect(x, y, w, h)                         rectángulo solo con borde (los arcos)
g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)   suaviza los bordes de lo que se dibuja
repaint()                                      pide que se vuelva a dibujar el panel
```

## Navegación, diálogos y mensajes

```java
cards.show(this, "Home menu")         cambia de página en el CardLayout (MainPanel.showPage)
JOptionPane.showMessageDialog(null, "texto")   mensaje rápido con un OK
new JDialog(ventana, "titulo", Dialog.ModalityType.APPLICATION_MODAL)   ventana emergente
                                      modal = bloquea el resto hasta que se cierre
.setLocationRelativeTo(this)          centra la ventana sobre el panel
.setResizable(false)                  no se puede cambiar el tamaño
.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)   la X no la cierra, solo los botones
.dispose()                            cierra y libera el diálogo
.pack()                               ajusta la ventana al tamaño preferido de lo que tiene adentro
```

## Juego (GamePanel)

```java
new Timer(1000 / Match.FPS, listener)   game loop: llama update() FPS veces por segundo
gameLoop.start() / gameLoop.stop()    arranca / detiene el juego (así funciona la pausa)
addKeyListener(keyH)                  el panel escucha el teclado (KeyHandler guarda qué tecla está presionada)
requestFocusInWindow()                le da el foco al panel para que reciba las teclas
```

## Estructura de cada panel

```java
prepareXPage()                        arma la vista: crea y acomoda los componentes
prepareController(main)               conecta los botones con lo que hacen (addActionListener)
```
