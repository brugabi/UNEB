package Questao1;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;

    public Server(int port){
        this.port = port;
    }

    public void start(){
        try {
            ServerSocket server = new ServerSocket(this.port);
            Socket cliente = server.accept();
            System.out.println("Cliente conectado na porta: " + cliente.getInetAddress().getHostAddress());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            saida.writeObject("Vaitimbora cliente ruim");

            saida.close();
            cliente.close();

        }
        catch (Exception e){
            System.out.println("Erro no server " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(12345);
        server.start();
    }
}
