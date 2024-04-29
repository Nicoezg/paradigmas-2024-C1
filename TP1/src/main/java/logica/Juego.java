package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Juego {
    private static final Vector2D DIMENSION_INICIAL = new Vector2D(30, 20);

    private final ArrayList<Robot> robots;

    private final Vector2D dimensiones;

    private final ArrayList<Fuego> celdasIncendiadas;

    private int nivel;

    private Corredor jugador;


    public Juego(){
        this.nivel = 1;
        this.dimensiones = DIMENSION_INICIAL;
        this.robots = new ArrayList<>();
        this.celdasIncendiadas = new ArrayList<>();
        this.jugador = new Corredor(new Vector2D(dimensiones.getX() / 2, getDimensionY()/2));
    }

    public void generarNivel(){
        var rand = new Randomizer(dimensiones.getX(), dimensiones.getY(), jugador.getPosicion());

        for (int i = 0; i < nivel * 4 && i < dimensiones.getX() * dimensiones.getY() - 2 ; i++){
            robots.add(new RobotX1(rand.nextVectorRandom(), jugador));
        }

        for (int i = 0; i < nivel * 2 && i < dimensiones.getX() * dimensiones.getY() - 2 ; i++){
            robots.add(new RobotX2(rand.nextVectorRandom(), jugador));
        }
    }

    public void avanzarNivel(){
        this.nivel++;
        this.jugador.aumentarTpSafe();
        this.jugador.moverse(dimensiones.getX() / 2, dimensiones.getY() / 2);
        cargarNivel();
    }

    public void cargarNivel(){
        this.robots.clear();
        this.celdasIncendiadas.clear();
        generarNivel();
    }

    public void reiniciarNivel(){
        this.nivel = 1;
        jugador = new Corredor(new Vector2D(dimensiones.getX() / 2, dimensiones.getY() / 2));
        cargarNivel();
    }

    public void cambiarDimensiones(int x, int y){
        this.dimensiones.setX(x);
        this.dimensiones.setY(y);
        reiniciarNivel();
    }

    public boolean actualizarJuego(Vector2D posicionDeseada) {
        /* Devuelve true en caso de que el que se movieron todas las entidades y el jugador no perdio, false en caso de que
        haya perdido. */
        jugador.moverse(posicionDeseada.getX(), posicionDeseada.getY());
        for (Robot robot: robots) {
            robot.perseguir();
        }
        HashMap<Vector2D,ArrayList<Entidad>> posiciones = buscarPosiciones();

        if (!detectarColisiones(posiciones)){
            return false;
        }
        if (jugadorGano()) {
            return true;
        }
        for (Robot robot: robots) {
            if (robot.getClass() == RobotX2.class) {
                robot.perseguir();
            }
        }
        posiciones = buscarPosiciones();
        return detectarColisiones(posiciones);

    }

    public void teletransportacionSegura(){
        HashMap<Vector2D,ArrayList<Entidad>> posicionesOcupadas = buscarPosiciones();
        Random random = new Random();
        Vector2D posicionSegura = new Vector2D(random.nextInt(0,getDimensionX()),random.nextInt(0,getDimensionY()));

        while (posicionesOcupadas.containsKey(posicionSegura)){
            posicionSegura.setX(random.nextInt(0,getDimensionX()));
            posicionSegura.setY(random.nextInt(0, getDimensionY()));
        }
        jugador.moverse(posicionSegura.getX(), posicionSegura.getY());

    }

    public int getDimensionX(){return dimensiones.getX();}

    public int getDimensionY(){return dimensiones.getY();}

    public Corredor getCorredor(){return jugador;}

    public ArrayList<Robot> getRobots(){return robots;}

    public ArrayList<Fuego> getCeldasIncendiadas(){return celdasIncendiadas;}

    public boolean jugadorGano(){
        return robots.isEmpty();
    }

    public int getNivel(){
        return nivel;
    }

    private boolean detectarColisiones(HashMap<Vector2D,ArrayList<Entidad>> posiciones){
        /* Recorre el hashmap de posiciones, corroborando que si en una posición hay más de 1 entidad,
        que se resuelva la colisión. */

        for (HashMap.Entry<Vector2D, ArrayList<Entidad>> entry: posiciones.entrySet()){
            ArrayList<Entidad> entidades = entry.getValue();

            if (entidades.size() > 1){
                if (!resolverColision(entidades)){return false;}
            }
        }
        return true;
    }


    private boolean resolverColision(ArrayList<Entidad> entidades) {
        /* Aprovechando que el corredor siempre está primero en el ArrayList asociado a su vector posición,
        se fija si el jugador es el primero. En caso de serlo, reinicia el nivel.
        Si el ArrayList de entidades no contiene fuego, explota un robot, generando un fuego y luego se elimina
        todos los robots que se encuentran en el ArrayList. */

        if (entidades.getFirst().equals(jugador)) {
            reiniciarNivel();
            return false;

        }else if (!containsFuego(entidades)){
            Fuego fuego = ((Robot) entidades.getFirst()).explotar();
            celdasIncendiadas.add(fuego);
        }

        for (Entidad entidad: entidades) {
            if (entidad instanceof Robot) {
                robots.remove(entidad);
            }
        }

    return true;
    }

    private HashMap<Vector2D,ArrayList<Entidad>> buscarPosiciones(){
        /* Construye un HashMap con las claves siendo los vectores de posición y los valores siendo un ArrayList de entidades.
        Se asegura que el corredor quede siempre primero en el ArrayList asociado a su posición. */

        HashMap<Vector2D, ArrayList<Entidad>> posiciones = new HashMap<>();
        ArrayList<Entidad> posCorredor = new ArrayList<>();
        posCorredor.add(jugador);
        posiciones.put(jugador.getPosicion(), posCorredor);

        for (Robot robot: robots) {

            if (posiciones.containsKey(robot.getPosicion())){
                posiciones.get(robot.getPosicion()).add(robot);

            } else {
                ArrayList<Entidad> arr = new ArrayList<>();
                arr.add(robot);
                posiciones.put(robot.getPosicion(),arr);
            }

            for (Fuego fuego: celdasIncendiadas) {
                if (posiciones.containsKey(fuego.getPosicion())) {
                    posiciones.get(fuego.getPosicion()).add(fuego);
                } else {
                    ArrayList<Entidad> arr = new ArrayList<>();
                    arr.add(fuego);
                    posiciones.put(fuego.getPosicion(),arr);
                }
            }
        }
        return posiciones;
    }

    private boolean containsFuego(ArrayList<Entidad> entidades) {
        for (Entidad entidad : entidades) {
            if (entidad instanceof Fuego) return true;
        }
        return false;
    }
}
