package Vista;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logica.Juego;
import logica.Vector2D;

import java.io.FileNotFoundException;

public class Settings {

    /* Se encarga de la interfaz gráfica del menú de opciones, así como también de procesar
    los cambios a realizar al modificar la dimensión y crear un nuevo juego. */

    private static final int PADDING = 30;
    private static final String TITULO = "Settings";
    private static final int ANCHO = 200;
    private static final int ANCHO_CHOICE_BOX = 150;
    private static final int ALTO_CHOICE_BOX = 20;
    private static final Vector2D[] OPCIONES_DIMENSIONES = new Vector2D[]{new Vector2D(10,15), new Vector2D(20,30), new Vector2D(30,45)};
    private static final String NUEVO_JUEGO = "New game";
    private static final String MSG_GRID_SIZE = "Grid size:";

    private final VBox vBox;
    private final Label elegirGridSize;
    private final Button juegoNuevo;
    private final ChoiceBox<Vector2D> choiceBox;
    private final Scene scene;
    private final Scene secondScene;
    private final Stage primaryStage;
    private final Juego juego;
    private final Interactuables interactuables;
    private final Impresion impresion;



    public Settings(Scene scene, Stage primaryStage, Juego juego, Interactuables interactuables,Impresion impresion) {
        choiceBox = new ChoiceBox<>();
        elegirGridSize = new Label(MSG_GRID_SIZE);
        juegoNuevo = new Button(NUEVO_JUEGO);
        vBox = new VBox(PADDING);
        this.scene = scene;
        this.juego = juego;
        this.interactuables = interactuables;
        this.impresion = impresion;
        this.primaryStage = primaryStage;
        configureChoiceBox();
        configureButton();
        configureVBox();
        secondScene = new Scene(vBox, ANCHO, ANCHO);
    }
    public void setListeners(){
        setNewGameListener();
        setSettingsListener();
    }

    private void configureChoiceBox() {
        choiceBox.setPrefWidth(ANCHO_CHOICE_BOX);
        choiceBox.setPrefHeight(ALTO_CHOICE_BOX);
        choiceBox.getItems().addAll(OPCIONES_DIMENSIONES);
        choiceBox.setValue(OPCIONES_DIMENSIONES[0]);
    }

    private void configureButton(){
        juegoNuevo.setAlignment(Pos.BOTTOM_CENTER);
    }

    private void configureVBox(){
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(elegirGridSize, choiceBox, juegoNuevo);
    }

    private void setNewGameListener(){
        juegoNuevo.setOnAction(event -> {
            Vector2D vector = choiceBox.getValue();
            juego.cambiarDimensiones(vector.getY(), vector.getX());
            try {
                juego.reiniciarNivel();
                interactuables.resetGridPane();
                impresion.dibujarGrilla();
                interactuables.setRectangleListener();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setSettingsListener(){
        this.scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                Stage secondaryStage = new Stage();
                secondaryStage.initOwner(primaryStage);
                secondaryStage.initModality(Modality.WINDOW_MODAL);
                secondaryStage.setResizable(false);
                secondaryStage.setTitle(TITULO);
                secondaryStage.setAlwaysOnTop(true);
                secondaryStage.setScene(secondScene);
                secondaryStage.show();
            }
        });
    }
}