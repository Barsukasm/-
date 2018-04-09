import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AntWorker extends Ant {

    public double x2, y2;
    public static int V = 5;
    protected int T = 0;
    boolean inOut = false;
    public AntWorker(int x, int y, int curTime) {
        super(x, y, curTime);
        double a = 0 - x;
        double b = 0 - y;
        x2 = a/Math.sqrt(a*a+b*b);
        y2 = b/Math.sqrt(a*a+b*b);
    }

    public static File file = new File("Images/AntWorker.png");
    public static Image img;
    public static int lifeTime = 1;

    static {

        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
        }
    }


    @Override
    public void move(){
        if(dx<=0&&dy<=0) {
            inOut = true;
            T = 1;
        } else if (dx>=x1&&dy>=y1){
            inOut = false;
            T = 1;
        }
        if(!inOut){
            dx = x1 + x2*V*T;
            dy = y1 + y2*V*T;
        } else {
            dx = 0 - x2*V*T;
            dy = 0 - y2*V*T;
        }
        T++;
    }

}
