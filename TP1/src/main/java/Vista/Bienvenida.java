package Vista;

import javafx.scene.control.Alert;

public class Bienvenida {

    /* Alerta de bienvenida, le da la bienvenida al jugador y le facilita los controles. */

    private static final String TITULO_ALERTA = "Bienvenido!";
    private static final String MENSAJE_ALERTA = "Bienvenido a Robots!";
    private static final String CONTENIDO_ALERTA = "Click para moverse, con ESC se pueden cambiar las dimensiones del juego";

    private final Alert alert;

    public Bienvenida() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TITULO_ALERTA);
        alert.setHeaderText(MENSAJE_ALERTA);
        alert.setContentText(CONTENIDO_ALERTA);
    }

    public void mostrar() {
        alert.showAndWait();
    }
}
