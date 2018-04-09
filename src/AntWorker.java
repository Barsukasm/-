import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AntWorker extends Ant {

    public AntWorker(int x, int y, int curTime) {
        super(x, y, curTime);
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
        dx+=10;
        dy+=10;
    }

}
