package logica;

public class Corredor extends Entidad{
    private int tps = 1;

    public Corredor(Vector2D posicion) {
        super(posicion);
    }
    public void aumentarTpSafe(){
        this.tps+=1;
    }

    public void moverse(int x, int y) {
        getPosicion().setX(x);
        getPosicion().setY(y);
    }

    public int getTps() {
        return tps;
    }

    public void usarTpSafe() {
        this.tps--;
    }
}
