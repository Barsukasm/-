import java.util.Vector;

public class WorkerAI extends BaseAI {

    public WorkerAI(Vector<Ant> vec){
        super(vec);
    }

    @Override
    public void antLogic() {
        if (av!=null){
            synchronized (av){
                for (Ant a:av){
                    if (a instanceof AntWorker){
                        a.move();
                    }
                }
            }
        }
    }
}
