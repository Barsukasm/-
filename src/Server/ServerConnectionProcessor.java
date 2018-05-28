package Server;
import Client.Request;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.scenario.effect.GaussianShadow;

import java.io.*;
import java.net.Socket;
import java.util.UUID;



public class ServerConnectionProcessor extends Thread {
    UUID uuid;
    private Socket socket;
    DataOutputStream outStream;
    DataInputStream inStream;
    Gson gson = new Gson();
   // boolean running;

    public ServerConnectionProcessor(Socket s, UUID id){
        socket=s;
        uuid = id;
        try{
            inStream = new DataInputStream(s.getInputStream());
            outStream = new DataOutputStream(s.getOutputStream());
            Request request = new Request("initial connection",id.toString(), Server.users);
            String rq = gson.toJson(request);
            outStream.write(rq.getBytes());
        }catch (Exception ex){ex.getStackTrace();}
    }

    @Override
    public void run() {
        System.out.println("Thread started: " + socket.getLocalSocketAddress() + " ID: " + uuid);
        while (true){
            try {
                byte[] bytes = new byte[1024];
                inStream.read(bytes);
                String string = new String(bytes);
                JsonReader reader = new JsonReader(new StringReader(string));
                reader.setLenient(true);
                Request request;
                request = gson.fromJson(reader,Request.class);
                switch (request.getCommand()){
                    case "choose":
                        System.out.println("Requested: " + string);
                        for (int i=0;i<Server.users.size();i++){
                            if (request.getRequestedID().equals(Server.users.get(i))){
                                Socket sk = Server.sockets.get(i);
                                sk.getOutputStream().write(bytes);
                            }
                        }
                        break;
                    case "copy":
                        System.out.println("Send copy to " + string);
                        for (int i = 0;i<Server.users.size();i++){
                            if (request.getRequestedID().equals(Server.users.get(i))){
                                Socket sk = Server.sockets.get(i);
                                sk.getOutputStream().write(bytes);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }catch (Exception ex){
                System.out.println(ex);
                try {
                    inStream.close();
                    outStream.close();
                    socket.close();
                    Server.users.remove(uuid.toString());
                    Server.sockets.remove(socket);
                    for (int i=0;i<Server.sockets.size();i++){
                        Server.out = Server.sockets.get(i).getOutputStream();
                        Request request = new Request("update",uuid.toString(),Server.users);
                        String rq = gson.toJson(request);
                        System.out.println("update all sockets on exeption" + rq);
                        Server.out.write(rq.getBytes());
                    }
                    stop();
                    //running = false;
                }catch (IOException ex1){
                    ex1.printStackTrace();
                }
            }
        }
    }
}
