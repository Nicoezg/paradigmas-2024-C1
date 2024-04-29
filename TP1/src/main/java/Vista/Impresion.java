package Vista;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logica.Juego;
import java.io.FileNotFoundException;

public class Impresion {

    /* Se encarga de imprimir la grilla, también delega la creación de las imagenes a la clase Imagenes. */

    private final Juego juego;
    private final GridPane gridPane;
    private final int ancho;
    private final int alto;

    public Impresion(Juego juego, int ancho, int largo) {
        this.juego = juego;
        this.gridPane = new GridPane();
        this.ancho = ancho;
        this.alto = largo;
    }
    public void dibujarGrilla() throws FileNotFoundException {
        Imagenes imagenes = new Imagenes();
        for (int i = 0; i < juego.getDimensionY(); i++) {
            for (int j = 0; j < juego.getDimensionX(); j++) {
                Rectangle rectangle = new Rectangle((double) ancho / juego.getDimensionX(), (double) (alto - 50) / juego.getDimensionY());
                gridPane.add(rectangle, j, i);
                if ((i + j) % 2 == 0) {
                    rectangle.setFill(Color.LIGHTSLATEGRAY);
                } else if ((i + j) % 2 != 0) {
                    rectangle.setFill(Color.LIGHTSTEELBLUE);
                }

            }

        }
        imagenes.actualizarGridpane(juego, ancho, gridPane);
    }

    public GridPane getGridPane() {return gridPane;}
}
