package Server;

import Client.Request;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class Server {
    public static ArrayList<String> users = new ArrayList<>();
    public static ArrayList<Socket> sockets = new ArrayList<>();
    //public static boolean running = true;
    public static Gson gson = new Gson();
    static OutputStream out;

    public static void main(String[] args) {
        try {
            System.out.println("Server is running");
            int port = 8888;
            ServerSocket ss = new ServerSocket(port);
            while (true){
                Socket s = ss.accept();
                UUID uuid = UUID.randomUUID();
                users.add(String.valueOf(uuid));
                sockets.add(s);
                ServerConnectionProcessor pr = new ServerConnectionProcessor(s,uuid);
                pr.start();
                for(int i=0;i<sockets.size()-1;i++){
                    out = sockets.get(i).getOutputStream();
                    Request request = new Request("update",uuid.toString(),users);
                    String rq = gson.toJson(request);
                    System.out.println("update all sockets" + rq);
                    out.write(rq.getBytes());
                }
                System.out.println("End loop");
            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }


}
