package Client;

import java.io.Serializable;
import java.util.*;

public class Request implements Serializable {
    String command;
    String id;
    String requestedID;

    ArrayList<String> users;
    public Vector<Ant> ants;
    public HashSet<Integer> ids;
    public TreeMap<Integer,Integer> timeTree;

    public Request(String command,String id,ArrayList<String> users){
        this.command = command;
        this.id = id;
        this.users = users;
    }

    public Request(String command,String id, Vector<Ant> ants, HashSet<Integer> ids, TreeMap<Integer,Integer> timeTree){
        this.command = command;
        this.id = id;
        this.ants = ants;
        this.ids = ids;
        this.timeTree = timeTree;
    }

    public Request(){}

    public Request(String command, String id,String requestedID){
        this.command = command;
        this.id = id;
        this.requestedID = requestedID;
    }

    public Request(String command, String id){
        this.command = command;
        this.id = id;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setRequestedID(String requestedID) {
        this.requestedID = requestedID;
    }

    public String getRequestedID() {
        return requestedID;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getUsers() {
        return users;
    }
}
