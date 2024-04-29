package logica;

import java.util.Objects;

public class Vector2D {
    private int x;

    private int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY( int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (Objects.equals(o.getClass(), Vector2D.class)){
            return ((Vector2D) o).getX() == x && ((Vector2D) o).getY() == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public Vector2D transformador(Vector2D click){
        int x = 0;
        int y = 0;
        if (click.getX() > this.x ){
            x += 1;
        }
        if (click.getX() < this.x) {
            x -= 1;
        }
        if (click.getY() > this.y) {
            y += 1;
        }
        if (click.getY() < this.y) {
            y -= 1;
        }
        return new Vector2D(this.x + x, this.y + y);
    }
}