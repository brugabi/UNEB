import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class ServidorTCP {
    public static void main(String[] args) {

        ArrayList<Agenda> clientes = new ArrayList<Agenda>();
        clientes.add(new Agenda("Pedro","pedroalvares@gmail.com","696662424"));
        clientes.add(new Agenda(("Joao", "joaocristo@gmail.com", "3131"));
        clientes.add(new Agenda("Carla", "carlaperez@loiratchan.com", "717171"));

        try {
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta: 12345");
            while(true){
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                saida.flush();
                saida.writeObject("string");
                saida.close();
                cliente.close();
            }
        }
        catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
}