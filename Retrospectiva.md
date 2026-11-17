# Retrospectiva — DOPOSoccer, Versión uno (Presentación + Dominio)

**Autores:** Iván Bernal · César Malaver
**Entrega:** S15, domingo 15 de noviembre de 2026 (a evaluador, preliminar)

### 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.

- **Ciclo 1 — Estructura y maqueta (1 al 8 de nov):** diagrama de clases, boceto de la interfaz y
  todas las pantallas de configuración: carga con selección de resolución, menú de inicio, popup de
  configuración del partido, selección de modalidad, selección de personaje y página de
  estadísticas. En el dominio, la definición de las clases principales. Empezamos por aquí porque
  la maqueta y el diagrama nos obligaban a ponernos de acuerdo en qué clases existían antes de
  programar la lógica.
- **Ciclo 2 — Juego básico (11 al 14 de nov):** dibujar a los jugadores, moverlos con el teclado,
  saltar, el balón con gravedad, las colisiones jugador–balón y la patada. Es el núcleo del juego.
- **Ciclo 3 — Partido completo Jugador vs Jugador (15 de nov):** arcos y goles, los dos tipos de
  partido (por tiempo con muerte súbita y por goles), validación de las condiciones del partido,
  marcador que se actualiza, mejora de estadísticas con puntos, poderes y efectos, pausa, mensaje
  con el ganador y los puntos, y pruebas de unidad. Con este ciclo se cierra el "nivel básico" que
  pide la Versión uno.

### 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿Por qué?

Los tres mini-ciclos están cerrados y se puede jugar un partido completo Jugador vs Jugador.
Quedan pendientes, algunos porque corresponden a versiones posteriores y otros por tiempo:

- **Jugador vs Máquina** y **guardar/abrir partidas:** son de la Versión dos. Por ahora muestran
  el aviso de "funcionalidad en construcción".
- **Aturdimiento:** la vida baja hasta 0, pero el jugador todavía no queda aturdido 3 segundos.
- **Log de errores para programadores:** aún no está implementado.
- **Bug conocido:** si el balón queda entre los dos jugadores y uno se acerca al otro, el balón
  los atraviesa. Se debe al orden en que se resuelven las colisiones.

### 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas)

| Autor | Horas |
|---|---|
| Iván | 45 |
| César | 41 |

### 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?

Tener un partido Jugador vs Jugador jugable de principio a fin, con poderes, efectos, marcador y
mensaje de ganador, manteniendo separadas la capa de presentación y la de dominio. La interfaz
solo le pide datos a `DopoSoccer`, y eso nos permitió trabajar en paralelo: uno en las pantallas
y otro en la lógica, sin pisarnos.

### 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?

La física del balón. Primero el balón se salía de la cancha, y después aparecieron casos en los
que atravesaba a los jugadores. 

En la interfaz, el reto fue que todo se viera bien en las tres resoluciones. Lo resolvimos
calculando todos los tamaños como fracciones de `GAME_SIZE` y reconstruyendo las pantallas al
cambiar la escala.

### 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?

Nos repartimos bien el trabajo por capas, trabajamos en ramas y las integramos con merges y pull
requests sin perder código.

Nos comprometemos a:
- **No dejar la mayor parte del trabajo para el último día:** el 15 de noviembre hicimos más de
  30 commits.
- **Escribir las pruebas de unidad a la par del código** y no al final.


### 7. Considerando las prácticas XP incluidas en los laboratorios, ¿cuál fue la más útil? ¿Por qué?

*Integrate often.* Como cada uno trabajaba en una capa distinta, integrar seguido  nos permitió detectar pronto cuando la presentación esperaba algo que el dominio
todavía no ofrecía, y corregirlo antes de que se acumularan los conflictos.

### 8. ¿Qué referencias usaron? ¿Cuál fue la más útil? Incluyan citas con estándares adecuados.

- Alley B. (s. f.). *How to use a CardLayout in Java* [Video]. YouTube.
  https://www.youtube.com/watch?v=Dex6-4j8xG8
- Bro Code. (s. f.). *Java pong game* [Video]. YouTube. https://youtu.be/oLirZqJFKPE
- Chris Courses. (s. f.). *How to Code: Gravity* [Video]. YouTube. https://youtu.be/3b7FyIxWW94
- GamesWithGabe. (s. f.). *Circle vs Shape Collision Detection | Coding a 2D Physics Engine in
  Java #10* [Video]. YouTube. https://youtu.be/cXryhth2Tzo
- GamesWithGabe. (s. f.). *Iterative Linear Impulse Resolution | Coding a 2D Physics Engine in
  Java #15* [Video]. YouTube. https://youtu.be/XKzNb381_eE
- Newcastle University. (s. f.). *Physics – Collision Response* [PDF].
  https://research.ncl.ac.uk/game/mastersdegree/gametechnologies/physicstutorials/5collisionresponse/Physics%20-%20Collision%20Response.pdf
- RyiSnow. (s. f.). *Game Loop and Key Input – How to Make a 2D Game in Java #2* [Video]. YouTube.
  https://youtu.be/VpH33Uw-_0E
- University of Colorado Boulder. (s. f.). *PhET Collision Lab* [Simulación].
  https://phet.colorado.edu/sims/html/collision-lab/latest/collision-lab_all.html

La lista completa, con la parte del código donde se usó cada referencia, está en
[docs/References.md](docs/References.md).

La más útil fue la serie *Coding a 2D Physics Engine in Java* de GamesWithGabe, porque está en
Java y explica la detección y la respuesta a colisiones entre círculos, que es justo lo que
necesitábamos para el balón y los jugadores.
