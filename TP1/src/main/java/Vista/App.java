package Vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logica.Juego;

import java.io.FileNotFoundException;

/**
 * JavaFX App
 */


public class App extends Application {
    private static final int ALTO = 650;
    private static final int ANCHO = 900;
    private static final String TITULO = "Robots";


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Bienvenida bienvenida = new Bienvenida();
        bienvenida.mostrar();

        InterfazBotones interfazBotones = new InterfazBotones();
        Juego juego = new Juego();
        juego.generarNivel();

        Impresion impresion = new Impresion(juego, ANCHO, ALTO);
        impresion.dibujarGrilla();
        Interactuables interactuables = new Interactuables(juego, impresion, impresion.getGridPane());
        interactuables.setListeners();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(impresion.getGridPane(), interfazBotones.getHBox());
        interfazBotones.agregarBotones(interactuables.getTpRandom(), interactuables.getTpSafe(), interactuables.getWait());

        Scene scene = new Scene(vbox, ANCHO, ALTO);
        Settings opciones = new Settings(scene, primaryStage, juego, interactuables,impresion);
        opciones.setListeners();

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(TITULO);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

