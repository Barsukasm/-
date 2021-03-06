package Client;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class NetThread extends Thread {
    private final Habitat habitat;
    Socket sock;
    DataOutputStream outStream;
    DataInputStream inStream;
    Gson gson = new Gson();
   // public boolean running = false;

    public NetThread(Habitat owner, Socket s){
        habitat = owner;
        sock = s;
        try {
            outStream = new DataOutputStream(sock.getOutputStream());
            inStream = new DataInputStream(sock.getInputStream());
        }catch (IOException ex){ex.printStackTrace();}
    }

    @Override
    public synchronized void start() {
     //   running=true;
        super.start();
    }

    @Override
    public void run() {
        Request request;
        System.out.println("This thread is working");
       while (true){
           try {
               byte[] bytes = new byte[1024];
               inStream.read(bytes);
               String string = new String(bytes);
               System.out.println("Readed string: " + string);
               JsonReader reader = new JsonReader(new StringReader(string));
               reader.setLenient(true);
               request = gson.fromJson(reader,Request.class);
               Vector<AntMes> antsMes = new Vector<>();
               Vector<Ant> antToAdd = new Vector<>();
               switch (request.getCommand()){
                   case "update":
                       System.out.println("update list");
                       habitat.users.clear();
                       habitat.users = request.getUsers();
                       habitat.f.updateUsersList();
                       break;
                   case "initial connection":
                       System.out.println("Initial connect");
                       habitat.id = request.getId();
                       habitat.users = request.getUsers();
                       habitat.f.updateUsersList();
                       break;
                   case "choose":
                       antsMes.clear();
                       for (int i=0;i<habitat.ants.size();i++){
                           Ant ant = habitat.ants.get(i);
                           String type = ant instanceof AntWorker ? "worker" : "warrior";
                           antsMes.add(new AntMes((int)ant.getx(), (int)ant.gety(), ant.getId(), type));
                       }
                       Request request1 = new Request("copy", request.getId(), antsMes,habitat.ids,habitat.timeTree);
                       String rq = gson.toJson(request1);
                       System.out.println("Send data to " + rq);
                       outStream.write(rq.getBytes());
                       break;
                   case "copy":
                       habitat.ants.clear();
                       for(int i=0; i<request.ants.size(); i++) {
                           AntMes newAM = request.ants.get(i);
                           Ant ant = newAM.getType().equals("worker") ? new AntWorker() : new AntWarrior();
                           ant.setx(newAM.getx());
                           ant.sety(newAM.gety());
                           ant.setId(newAM.getId());
                           antToAdd.add(ant);
                       }
                       habitat.ants = antToAdd;
                       habitat.ids.clear();
                       habitat.ids = request.ids;
                       habitat.timeTree.clear();
                       habitat.timeTree = request.timeTree;
                       habitat.elapsed = 0;
                       break;
                   default:
                       System.out.println("default");
                       break;
               }

           }catch (Exception ex){
               System.out.println(ex.getMessage());
               try{
                   sock.close();
                   inStream.close();
                   stop();
                   //running = false;
               }catch (IOException ex1){
                   ex1.printStackTrace();
               }
           }
       }
    }
}
