package Questao3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Server {

    private final int port;
    Map<String,String> dict;

    public Server(int port, Map<String,String> dict){
        this.port = port;
        this.dict = dict;
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(this.port);

            while(true) {
                Socket cliente = server.accept();
                System.out.println("Cliente conectado na porta: " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                saida.writeObject("Diga qual a palavra que quer o significado");
                String msg = (String) entrada.readObject();
                saida.writeObject(dict.get(msg));
            }
        } catch (Exception e) {
            System.out.println("Erro no server :" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Dicionario dict = new Dicionario();
        dict.adicionarPalavra("cachorro", "animal doméstico de quatro patas");
        dict.adicionarPalavra("gato", "animal doméstico de hábitos noturnos");
        dict.adicionarPalavra("computador", "dispositivo eletrônico para processamento de dados");
        Server server  = new Server(12345, dict.getDicionario());
        server.start();


    }
}
