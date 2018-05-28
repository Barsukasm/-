package Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;

public class Console extends JDialog {

    DataOutputStream pw;
    PipedOutputStream po = new PipedOutputStream();
    JTextArea entr = new JTextArea();
    JScrollPane pane = new JScrollPane(entr);
    public Habitat hb;
    String msg;


    public Console(JFrame owner, Habitat h){
        super(owner,"Console", false);
        setBounds(500,250,500,200);
        add(pane);
        pane.setSize(getSize());
        entr.setSize(getSize());
        pw = new DataOutputStream(po);
        hb = h;
        entr.addKeyListener(keyAdapter);
        clear();
    }

    public void clear(){
        entr.setText("> ");
        entr.setCaretPosition(2);
    }

    public void cmd(String command){
        msg = null;
        if (command.equalsIgnoreCase("start")){
            if (hb.running){
                msg = "Simulation already started";
            }else {
                hb.command_Start();
                msg = "Simulation started";
            }
        }else {
            if (command.equalsIgnoreCase("stop")){
                if (!hb.running){
                    msg = "Simulation is not running";
                }else{
                    hb.command_stop();
                    msg = "Simulation stopped";
                }
            }
        }
        if (command.equalsIgnoreCase("clear")){
            entr.setText("");
            msg = "";
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                entr.append((msg==null ? "Wrong command": msg) + "\n> ");
            }
        });

    }

    public PipedOutputStream getStream(){
        return po;
    }


    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_ENTER:
                    try{
                        int offset = entr.getLineOfOffset(entr.getCaretPosition());
                        int start = entr.getLineStartOffset(offset) + 2;
                        int end = entr.getLineEndOffset(offset);
                        String str = entr.getText(start, (end - start));
                        pw.writeUTF(str);
                    } catch (BadLocationException | IOException ex){
                        ex.printStackTrace();
                    }
            }
        }
    };




}
