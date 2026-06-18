import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Q1Server {

    public static void main(String[] args) {

        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                String text;

                do {
                    text = reader.readLine();
                    writer.println("Server: " + text);
                }while (!text.equals("bye"));

                socket.close();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
