package Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class InterfazBotones {

    /* Se encarga de crear la interfaz gráfica de los botones. */

    private final HBox hBox;

    public InterfazBotones() {
        this.hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
    }

    public HBox getHBox() {
        return hBox;
    }

    public void agregarBotones(Button tpRandom, Button tpSafe, Button wait){
        hBox.getChildren().addAll(tpRandom, tpSafe, wait);
    }
}
