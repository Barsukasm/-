import java.util.Vector;

public class WarriorAI extends BaseAI {

    public WarriorAI(Vector<Ant> vec){
        super(vec);
    }

    @Override
    public void antLogic() {
        if (av!=null){
            synchronized (av){
                while (pause){
                    try{
                        av.wait();
                    }catch (InterruptedException ex){}
                }
                for (Ant a:av){
                    if (a instanceof AntWarrior){
                        a.move();
                    }
                }
            }
        }
    }
}
