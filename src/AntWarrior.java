import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AntWarrior extends Ant {

    public AntWarrior(int x, int y) {
        super(x, y);
    }

    public static File file = new File("Images/AntWarrior.png");
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
