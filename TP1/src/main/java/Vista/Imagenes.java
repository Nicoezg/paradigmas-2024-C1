package Vista;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logica.Juego;
import logica.RobotX2;
import logica.Vector2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Imagenes {

    /* Se encarga del procesado y actualización de imagenes en la grilla. */

    private final static String PATH_CORREDOR = "src/main/resources/corredor.png";
    private final static String PATH_ROBOTX1= "src/main/resources/robot1a.png";
    private final static String PATH_ROBOTX2 = "src/main/resources/robot2a.png";
    private final static String FUEGO = "src/main/resources/fuego.png";

    public void actualizarGridpane(Juego juego, int ancho, GridPane gridPane) throws FileNotFoundException {
        FileInputStream pathCorredor = new FileInputStream(PATH_CORREDOR);
        Image runner = new Image(pathCorredor);
        ImageView imagenRunner = new ImageView(runner);
        imagenRunner.setFitWidth((double) ancho / juego.getDimensionX());
        imagenRunner.setPreserveRatio(true);
        Vector2D posCorredor = juego.getCorredor().getPosicion();
        gridPane.add(imagenRunner,posCorredor.getX(),posCorredor.getY());


        for (int i = 0; i < juego.getRobots().size(); i++) {
            FileInputStream pathRobot = new FileInputStream(PATH_ROBOTX1);
            if (juego.getRobots().get(i) instanceof RobotX2) {
                pathRobot = new FileInputStream(PATH_ROBOTX2);
            }

            Image robot = new Image(pathRobot);
            ImageView imagenRobot = new ImageView(robot);
            imagenRobot.setFitWidth((double) ancho / juego.getDimensionX());
            imagenRobot.setPreserveRatio(true);
            Vector2D pos_robot = juego.getRobots().get(i).getPosicion();
            gridPane.add(imagenRobot,pos_robot.getX(),pos_robot.getY());
        }

        for (int i = 0; i < juego.getCeldasIncendiadas().size(); i++) {
            FileInputStream pathFuego = new FileInputStream(FUEGO);
            Image fuego = new Image(pathFuego);
            ImageView imagenFuego = new ImageView(fuego);
            imagenFuego.setFitWidth((double) ancho /juego.getDimensionX());
            imagenFuego.setPreserveRatio(true);
            Vector2D posFuego = juego.getCeldasIncendiadas().get(i).getPosicion();
            gridPane.add(imagenFuego,posFuego.getX(),posFuego.getY());
        }
    }

}
