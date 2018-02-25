import java.util.TimerTask;

public class Updater extends TimerTask {

    private Habitat habitat = null;

    private boolean firstRun = true;

    private long startTime = 0;

    private long lastTime = 0;

    public Updater(Habitat hb) {
        habitat = hb;
    }

    @Override
    public void run() {
        if (firstRun) {
            startTime = System.currentTimeMillis();
            lastTime = startTime;
            firstRun = false;
        }

        long currentTime = System.currentTimeMillis();
        double elapsed = (currentTime - startTime) / 1000.0;
        habitat.update(elapsed, lastTime);
        lastTime = (long) elapsed;
    }
}
