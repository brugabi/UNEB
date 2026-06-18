package TesteOutroInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable{


    private Socket cliente;

    public ServerThread(Socket s){
        this.cliente = s;
    }
    @Override
    public void run() {
        try{
        InputStreamReader inputStreamReader = new InputStreamReader(this.cliente.getInputStream()) ;
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String msg = reader.readLine();
        System.out.println("Cliente: " + msg);
        PrintStream send = new PrintStream(this.cliente.getOutputStream());
        send.println("Bom Dia cliente");
        System.out.println("Ultima msg cliente: " + reader.readLine());
    }
        catch (Exception e){
        e.getMessage();
    }
    }
}
