package Vista;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logica.Juego;
import logica.Randomizer;
import logica.Vector2D;
import javafx.scene.control.ButtonType;
import java.io.FileNotFoundException;
import java.util.Optional;

public class Interactuables {

    /* Se encarga de la interacción con el usuario, procesando los eventos que transcurren a
     lo largo del desarrollo del juego. */

    private static final String MENSAJE_TP = "Teleport Randomly";
    private static final String MENSAJE_WAIT = "Wait for robots";
    private static final String CONTINUAR = "Continuar";
    private static final String REINICIAR = "Reiniciar";
    private static final String SALIR = "Salir";
    private static final String SIGUIENTE_NIVEL = "Ganaste, siguiente nivel!";
    private static final String CONFIRMACION_GANAR = "Quiere continuar?";
    private static final String CONFIRMACION_PERDER = "Quiere reiniciar?";
    private static final String PERDISTE ="Perdiste";
    private static final int ANCHO_BOTONES = 300;

    private static final int ALTO_BOTONES = 50;

    private static final Font FUENTE = Font.font(15);

    private final Button tpRandom;

    private final Button tpSafe;

    private final Button wait;

    private final Juego juego;

    private final GridPane gridPane;

    private final Impresion impresion;


    public Interactuables(Juego juego, Impresion impresion, GridPane gridpane) {
        this.tpRandom = new Button(MENSAJE_TP);
        this.tpSafe = new Button(String.format("Teleport Safely\n(Remaining: %d)", juego.getCorredor().getTps()));
        this.wait = new Button(MENSAJE_WAIT);
        this.gridPane = gridpane;
        this.juego = juego;
        this.gridPane.setAlignment(Pos.TOP_CENTER);
        this.impresion=impresion;
        configurarBotones();
    }


    public void resetGridPane() {
        this.gridPane.getChildren().clear();
    }

    public void setListeners(){
        setListenerTpRandom();
        setListenerTpSafe();
        setListenerWait();
        setRectangleListener();
    }

    public void setRectangleListener(){
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Node rectangle = gridPane.getChildren().get(i);
            enableTpSafe();

            rectangle.setOnMouseClicked(mouseEvent -> {
                Vector2D destino = new Vector2D(GridPane.getColumnIndex(rectangle), GridPane.getRowIndex(rectangle));
                destino = juego.getCorredor().getPosicion().transformador(destino);
                int nivel=juego.getNivel();
                if (!juego.actualizarJuego(destino)){
                    jugadorPerdio(nivel);
                }
                if (juego.jugadorGano()) {
                    jugadorGano();
                }

                try {
                    resetGridPane();
                    impresion.dibujarGrilla();
                    setRectangleListener();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }

    public Button getTpRandom(){
        return this.tpRandom;
    }

    public Button getTpSafe() {
        return this.tpSafe;
    }

    public Button getWait() {
        return this.wait;
    }

    private void configurarBotones(){
        tpRandom.setFont(FUENTE);
        wait.setFont(FUENTE);
        tpRandom.setAlignment(Pos.CENTER);
        tpSafe.setAlignment(Pos.CENTER);
        wait.setAlignment(Pos.CENTER);
        tpSafe.setTextAlignment(TextAlignment.CENTER);
        tpRandom.setPrefHeight(ALTO_BOTONES);
        tpRandom.setPrefWidth(ANCHO_BOTONES);
        tpSafe.setPrefHeight(ALTO_BOTONES);
        tpSafe.setPrefWidth(ANCHO_BOTONES);
        wait.setPrefHeight(ALTO_BOTONES);
        wait.setPrefWidth(ANCHO_BOTONES);
    }

    private void setListenerTpSafe() {
        tpSafe.setOnAction(actionEvent -> {
            juego.getCorredor().usarTpSafe();
            tpSafe.setText(String.format("Teleport Safely\n(Remaining: %d)", juego.getCorredor().getTps()));
            juego.teletransportacionSegura();

            if (juego.getCorredor().getTps() == 0) {
                tpSafe.setDisable(true);
            }
            try {
                resetGridPane();
                impresion.dibujarGrilla();
                setRectangleListener();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void setListenerWait() {
        wait.setOnAction(actionEvent -> {
            int nivel= juego.getNivel();
            if (!juego.actualizarJuego(juego.getCorredor().getPosicion())){
                jugadorPerdio(nivel);
            }
            if (juego.jugadorGano()) {
                jugadorGano();
            }
            enableTpSafe();

            try {
                resetGridPane();
                impresion.dibujarGrilla();
                setRectangleListener();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setListenerTpRandom() {
        tpRandom.setOnAction(actionEvent -> {
            Randomizer random = new Randomizer(juego.getDimensionX(), juego.getDimensionY(), juego.getCorredor().getPosicion());
            int nivel = juego.getNivel();
            if (!juego.actualizarJuego(random.nextVectorRandom())){
                jugadorPerdio(nivel);
            }
            if (juego.jugadorGano()){
                jugadorGano();
            }
            enableTpSafe();

            try {
                resetGridPane();
                impresion.dibujarGrilla();
                setRectangleListener();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void enableTpSafe(){
        if (juego.getCorredor().getTps() > 0) {
            this.tpSafe.setDisable(false);
            this.tpSafe.setText(String.format("Teleport Safely\n(Remaining: %d)", juego.getCorredor().getTps()));
        }
    }

    private void jugadorGano(){
        Alert alertaGano = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType botonSeguir = new ButtonType(CONTINUAR);
        ButtonType botonSalir = new ButtonType(SALIR);

        alertaGano.getButtonTypes().setAll(botonSalir, botonSeguir);
        alertaGano.setTitle(SIGUIENTE_NIVEL);
        alertaGano.setHeaderText(String.format("Siguiente nivel: %d", juego.getNivel() + 1));
        alertaGano.setContentText(CONFIRMACION_GANAR);
        Optional<ButtonType> result = alertaGano.showAndWait();

        if (result.isPresent() && result.get() == botonSeguir) {
            juego.avanzarNivel();
        } else {
            System.exit(1);
        }

    }

    private void jugadorPerdio(int nivel){
        Alert alertaPerdio = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType botonReiniciar= new ButtonType(REINICIAR);
        ButtonType botonSalir = new ButtonType(SALIR);

        alertaPerdio.setHeaderText(String.format("Perdiste en el nivel %d", nivel));
        alertaPerdio.getButtonTypes().setAll(botonSalir, botonReiniciar);
        alertaPerdio.setTitle(PERDISTE);
        alertaPerdio.setContentText(CONFIRMACION_PERDER);
        Optional<ButtonType> result = alertaPerdio.showAndWait();

        if (result.isPresent() && result.get() == botonReiniciar) {
            juego.reiniciarNivel();
        } else {
            System.exit(1);
        }

    }
}

