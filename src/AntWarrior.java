import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AntWarrior extends Ant {

    public int c1, c2;
    public static double V = 0.225;
    public static int R = 50;
    protected int T = 0;
    public AntWarrior(int x, int y, int curTime) {
        super(x, y, curTime);
        c1 = x + 50;
        c2 = y;
    }

    public static File file = new File("Images/AntWarrior.png");
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
        dx = c1 - R*(Math.cos(V*T));
        dy = c2 - R*(Math.sin(V*T));
        T++;
    }

}
