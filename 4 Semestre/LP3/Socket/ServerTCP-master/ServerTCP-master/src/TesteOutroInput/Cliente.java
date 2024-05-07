package TesteOutroInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private int port;
    private String host;


    public Cliente(int port, String host){
        this.port = port;
        this.host = host;
    }

    public void connect(){
        Scanner in = new Scanner(System.in);
        try {
            Socket cliente = new Socket(host, port);
            PrintStream send = new PrintStream(cliente.getOutputStream());
            send.flush();
            send.println("Bom Dia servidor, adeus");
            InputStreamReader inputStreamReader = new InputStreamReader(cliente.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            System.out.println("Servidor:" + reader.readLine());
            System.out.println("Diz algo ai cliente: ");
            String newMsg = in.nextLine();
            send.println(newMsg);
        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente( 12345, "localhost");
        cliente.connect();
    }
}
