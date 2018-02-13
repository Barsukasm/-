public abstract class Ant implements IBehaviour {

    private double dx,dy;

    public Ant(double x, double y){
        dx = x;
        dy = y;
    }

    @Override
    public abstract void move();

    @Override
    public double getx() {
        return dx;
    }

    @Override
    public double gety() {
        return dy;
    }

    @Override
    public void setx(double x) {
        dx=x;
    }

    @Override
    public void sety(double y) {
        dy=y;
    }
}
