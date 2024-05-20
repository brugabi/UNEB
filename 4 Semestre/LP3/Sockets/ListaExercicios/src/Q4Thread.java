import java.io.*;
import java.net.Socket;

public class Q4Thread extends Thread {
    private Socket socket;
    private Q4Server server;


    public Q4Thread(Socket socket, Q4Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run () {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
            objectOutputStream.writeObject();

            String msg;
            float lance;

            do {
                writer.

            }while (!msg.equals("quit"));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
