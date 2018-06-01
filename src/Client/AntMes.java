package Client;

public class AntMes {

    protected int dx, dy;
    protected int spawnTime, id;
    String type;

    //Конструктор по умолчанию
    public AntMes() {
        dx = 0;
        dy = 0;
        spawnTime = 0;
    }
    //Конструктор с параметрами
    public AntMes(int x, int y, int id, String type) {
        dx = x;
        dy = y;
        this.id = id;
        this.type = type;
    }


    public int getx() {
        return dx;
    }


    public int gety() {
        return dy;
    }


    public void setx(int x) {
        dx=x;
    }


    public void sety(int y) {
        dy=y;
    }


    public int getId() {
        return id;
    }

    public void setId(int id){this.id=id;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
