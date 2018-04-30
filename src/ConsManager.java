import java.io.DataInputStream;
import java.io.IOException;

public class ConsManager extends Thread {
    public DataInputStream pr;
    boolean running = false;
    public Habitat hb;

    ConsManager(Habitat habitat){
        hb = habitat;
    }

    public void setStream(DataInputStream pw){
        pr = pw;
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        while (running){
            try {
            String command = pr.readUTF().trim();
            hb.cd.cmd(command);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
