package Threads;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
    private int port;
    private ArrayList<Agenda> agendas = new ArrayList<>();

    public Server(int port){
        this.port = port;

    }

    public void start(){
        String email = "",nome = "",ano = "";
        Scanner in = new Scanner(System.in);
        try {
            String msg;
            ServerSocket servidor = new ServerSocket(this.port);
            System.out.println("Servidor Iniciado");
            while(true) {
                Socket cliente = servidor.accept();
                TesteThread testeThread = new TesteThread(cliente);
                Thread thread = new Thread(testeThread);
                thread.start();
                //Servidor espera algum cliente entrar
               /* Socket cliente = server.accept();
                System.out.println("Threads.Cliente conectado pela porta: " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                saida.flush();
                saida.writeObject("Diga o email");
                email = (String) entrada.readObject();
                // saida.writeObject(email);
                saida.flush();
                //Manda msg para o cliente
                saida.writeObject("Diga o nome");
                //Recebe msg do cliente
                nome = (String) entrada.readObject();
                saida.flush();
                saida.writeObject("Diga o ano");
                ano = (String) entrada.readObject();
                agendas.add(Threads.Agenda.criarAgenda(email,nome,ano));
                entrada.close();
                saida.close();
                cliente.close();
                for (Threads.Agenda a: agendas) {
                    System.out.println(a.getNome());
                } */
            }
        }
        catch (Exception e){
            System.out.println("Erro " + e.getMessage());
        }
    }



    public static void main(String[] args) {
        Server servidor = new Server(12345);
        servidor.start();
    }
}
