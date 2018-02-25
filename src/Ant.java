public abstract class Ant implements IBehaviour {

    protected int dx, dy;

    public Ant() {
        dx = 0;
        dy = 0;
    }

    public Ant(int x, int y) {
        dx = x;
        dy = y;
    }

    @Override
    public abstract void move();

    @Override
    public int getx() {
        return dx;
    }

    @Override
    public int gety() {
        return dy;
    }

    @Override
    public void setx(int x) {
        dx=x;
    }

    @Override
    public void sety(int y) {
        dy=y;
    }
}
