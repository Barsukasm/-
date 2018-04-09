public abstract class Ant implements IBehaviour {

    protected int x1, y1;
    protected double dx, dy;
    protected int spawnTime, id;
    public static int diapason = 10000;
//Конструктор по умолчанию
    public Ant() {
        dx = 0;
        dy = 0;
        x1 = 0;
        y1 = 0;
        spawnTime = 0;
        id = (int)(Math.random()*diapason);
    }
//Конструктор с параметрами
    public Ant(int x, int y, int curTime) {
        dx = x;
        dy = y;
        x1 = x;
        y1 = y;
        spawnTime = curTime;
        id = (int)(Math.random()*diapason);
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
    public void setx(int x) {
        dx=x;
    }

    @Override
    public void sety(int y) {
        dy=y;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void reroll(){
        id = 0;
        id = (int)(Math.random()*diapason);
    }
}
