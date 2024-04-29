package logica;

public class Robot extends Entidad{
    private final Corredor corredor;

    public Robot(Vector2D posicion, Corredor corredor){
        super(posicion);
        this.corredor=corredor;
    }

    public void perseguir (){
        posicion = posicion.transformador(corredor.getPosicion());
    }
    public Fuego explotar(){
        return new Fuego(this.posicion);
    }



}
