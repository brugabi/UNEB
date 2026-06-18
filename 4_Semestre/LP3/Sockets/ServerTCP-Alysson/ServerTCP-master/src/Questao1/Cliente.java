package Questao1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Cliente {
    private final int porta;
    private final String host;

    public Cliente(int porta, String host) {
        this.porta = porta;
        this.host = host;
    }

    public void connect(){
        try {
            Socket cliente = new Socket(host, porta);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            String msgRecebida = (String) entrada.readObject();
            System.out.println("Mensagem recebida pelo server: " + msgRecebida);
        }
        catch (Exception e){
            System.out.println("Erro no cliente: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        Cliente cliente1 = new Cliente(12345,"localhost");
        cliente1.connect();

    }
}
