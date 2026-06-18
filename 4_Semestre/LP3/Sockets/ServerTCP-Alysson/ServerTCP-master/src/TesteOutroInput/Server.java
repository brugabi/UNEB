package TesteOutroInput;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;

    public Server(int port){
        this.port = port;
    }


    public void start() {
        try {
            ServerSocket server = new ServerSocket(this.port);
            while (true) {
                Socket cliente = server.accept();
                ServerThread serverThread = new ServerThread(cliente);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public static void main(String[] args) {
        Server server = new Server(12345);
        server.start();
    }
}
