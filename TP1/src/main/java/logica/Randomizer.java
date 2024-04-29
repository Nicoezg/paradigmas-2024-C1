package logica;

import java.util.ArrayList;
import java.util.Collections;

public class Randomizer {
    private final ArrayList<Vector2D> vectores = new ArrayList<>();

    public Randomizer(int limiteX, int limiteY, Vector2D posicionJugador) {
        for (int i = 0; i < limiteX; i++) {
            for (int j = 0; j < limiteY; j++) {
                if (i == posicionJugador.getX() && j == posicionJugador.getY()){
                    continue;
                }
                vectores.add(new Vector2D(i, j));
            }
        }
        Collections.shuffle(vectores);
    }

    public Vector2D nextVectorRandom() {
        return vectores.removeFirst();
    }
}
