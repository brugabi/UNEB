import java.io.*;
import java.net.Socket;

public class Q4Client {

    public static void main(String[] args) {
        int port = 12345;

        try {
            Socket socket = new Socket("localhost", port);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String msg;
            do {



            } while (!msg.equals("quit"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
