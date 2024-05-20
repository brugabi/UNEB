import java.io.*;
import java.net.Socket;

public class Q1Cliente {

    public static void main(String[] args) {

        int port = 12345;

        try {
            Socket socket = new Socket("localhost", port);

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Console console = System.console();
            String text;

            do {
                text = console.readLine("Enter message: ");
                writer.println(text);

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String servermessage = reader.readLine();

                System.out.println(servermessage);

            }
            while (!text.equals("bye"));
            socket.close();

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
