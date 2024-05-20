import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String username = reader.readLine();
            server.addUserName(username);

            String servermsg = "New user connected: " + username;
            server.broadcast(servermsg, this);

            String clientmsg;

            do {
                clientmsg = reader.readLine();
                servermsg = "[" + username + "]:" + clientmsg;
                server.broadcast(servermsg,this);
            } while (!clientmsg.equals("quit"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        }
        else {
            writer.println("No other users connected");
        }
    }

    void sendMessage (String message) {
        writer.println(message);
    }
}
