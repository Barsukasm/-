import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class CurrentObjDia extends JDialog {


    TextArea msg = new TextArea("Some text");
    JButton ok = new JButton("Ok");

    public CurrentObjDia (JFrame owner){
        super(owner, "Current objects", true);
        setBounds(500,250,500,200);
        setLayout(new FlowLayout());
        add(msg);
        add(ok);
    }

    public void setMsg(Vector<Ant> a){
        String someText = "Type: "+"Identificator: "+"Creation time: " + "\n";
        for (Ant ant:a){
            if(ant instanceof AntWorker) someText+="Worker ";
            else someText+="Warrior ";
            someText += ant.id + " " + ant.spawnTime + "\n";
        }
        msg.setPreferredSize(new Dimension(450,100));
        msg.setText(someText);
    }
}
