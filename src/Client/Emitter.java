package Client;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

public class Emitter {
    private final OutputStream outStream;
    Socket socket;
    Habitat habitat;
    private Gson gson = new Gson();

    public Emitter(Socket socket, Habitat habitat) throws IOException{
        this.habitat = habitat;
        this.socket = socket;
        outStream = new DataOutputStream(socket.getOutputStream());
    }

    public void importAnts(String reqID) throws IOException{
        Request request = new Request("choose", habitat.id,reqID);
        String req = gson.toJson(request);
        System.out.println("Request to import from: " + req);
        outStream.write(req.getBytes());
    }
}
