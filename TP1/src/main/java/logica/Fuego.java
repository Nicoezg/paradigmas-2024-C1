package logica;

import java.util.Objects;

public class Fuego extends Entidad{
    public Fuego(Vector2D posicion){
        super(posicion);
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        return Objects.equals(o.getClass(), Fuego.class);

    }

}
