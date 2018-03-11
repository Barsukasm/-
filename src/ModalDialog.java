import javax.swing.*;
import java.awt.*;

public class ModalDialog extends JDialog {

    int size = 0, workers = 0, warriors = 0, elapsed = 0;
    TextArea msg = new TextArea("Some text");
    JButton ok = new JButton("Ok");
    JButton cancel = new JButton("Cancel");

    public ModalDialog (JFrame owner){
        super(owner, "Do you want to end simulation?", true);
        setBounds(500,250,500,200);
        setLayout(new FlowLayout());
        add(msg);
        add(ok);
        add(cancel);
    }

    public void setStats(int t, int w, int war, int e){
        size = t;
        workers = w;
        warriors = war;
        elapsed = e;
        msg.setPreferredSize(new Dimension(450,100));
        msg.setText("Time elapsed: "+elapsed+"\n" +
                "Ants generated: "+size+"\n"+
                "Workers generated:"+workers+"\n"+
                "Warriors generated: "+warriors);
        msg.setEditable(false);
    }
}
