package logica;


public class Entidad {
    protected Vector2D posicion;

    public Entidad(Vector2D posicion) {
        this.posicion = posicion;
    }

    public Vector2D getPosicion(){
        return posicion;
    }


}
