import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AntWorker extends Ant {

    public AntWorker(int x, int y) {
        super(x, y);
    }

    public static File file = new File("D:\\ProjectsJava\\TP Labs\\Images\\AntWorker.jpg");
    public static Image img;

    static {

        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
        }
    }


    @Override
    public void move(){

    }

}
