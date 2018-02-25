import java.util.Timer;

public class App {

    public static void main(String[] args) {
        Habitat habitat = new Habitat(3, 4, 0.5, 0.66);
        Timer timer = new Timer();
        timer.schedule(new Updater(habitat), 0, 100);
    }
}
