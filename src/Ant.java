public abstract class Ant implements IBehaviour {

    protected int dx, dy;
    protected int spawnTime, id;
    public static int diapason = 1000;
//Конструктор по умолчанию
    public Ant() {
        dx = 0;
        dy = 0;
        spawnTime = 0;
        id = (int)(Math.random()*diapason);
    }
//Конструктор с параметрами
    public Ant(int x, int y, int curTime) {
        dx = x;
        dy = y;
        spawnTime = curTime;
        id = (int)(Math.random()*diapason);
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

    @Override
    public int hashCode() {
        return id;
    }

    public void reroll(){
        id = 0;
        id = (int)(Math.random()*diapason);
    }
}
