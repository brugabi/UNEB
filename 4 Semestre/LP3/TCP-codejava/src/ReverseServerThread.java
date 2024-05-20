import java.net.ServerSocket;
import java.net.Socket;

/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 *
 * @author www.codejava.net
 */

public class ReverseServerThread {

    public static void main(String[] args) {

        int port = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket).start();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
