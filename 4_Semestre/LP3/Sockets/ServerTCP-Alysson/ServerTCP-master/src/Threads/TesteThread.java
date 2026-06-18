package Threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TesteThread implements Runnable {
    private final Socket socket;

    public TesteThread(Socket cliente) {
        this.socket = cliente;
    }

    @Override
    public void run() {
        String nome,email,ano;
        try {
            System.out.println("Threads.Cliente conectado pela porta: " + this.socket.getInetAddress().getHostAddress());
            ObjectOutputStream saida = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(this.socket.getInputStream());
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
           // agendas.add(Threads.Agenda.criarAgenda(email, nome, ano));
            entrada.close();
            saida.close();
            this.socket.close();
        }catch (Exception e){
            System.out.println("Erro no thread");
        }
    }

}
