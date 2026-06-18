import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void execute() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Chat server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New User connected");

               UserThread newUser = new UserThread(socket, this);
               userThreads.add(newUser);
               newUser.start();
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 12345;
        ChatServer server = new ChatServer(port);
        server.execute();
    }

    void broadcast (String msg, UserThread user) {
        for (UserThread aUser  : userThreads) {
            if(aUser != user) {
                aUser.sendMessage(msg);
            }
        }
    }

    void addUserName (String username) {
        userNames.add(username);
    }

    void removeUser (String username, UserThread aUser) {
        boolean removed = userNames.remove(username);
        if (removed){
            userThreads.remove(aUser);
            System.out.println("The User: " + username + " exit");
        }
    }
    Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}
