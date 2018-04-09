import java.util.Vector;

public abstract class BaseAI extends Thread {

    boolean going = false;
    boolean pause = false;
    Vector<Ant> av;

    public BaseAI(Vector<Ant> vec){
        av = vec;
    }


    @Override
    public void run(){
        while (going){
            antLogic();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException ex){}
            while (pause){
                try{
                    av.wait();
                }catch (InterruptedException ex){}
            }
        }

    }


    @Override
    public void start() {
        super.start();
        going = true;
    }


    public abstract void antLogic();
}
