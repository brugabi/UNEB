package Questao3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private final int porta;
    private final String host;

    public Cliente(int porta, String host) {
        this.porta = porta;
        this.host = host;
    }

    public void connect() {
        String msg;
        Scanner in = new Scanner(System.in);
        try {
            Socket cliente = new Socket(host, porta);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            String msgRecebida = (String) entrada.readObject();
            System.out.println("Mensagem recebida pelo server: " + msgRecebida);
            saida.writeObject(in.nextLine());
            msgRecebida = (String) entrada.readObject();
            System.out.println("Mensagem recebida pelo server: " + msgRecebida);

        } catch (Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente(12345, "localhost");
        cliente.connect();
    }
}
