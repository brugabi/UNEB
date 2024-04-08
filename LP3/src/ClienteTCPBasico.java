import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Date;

public class ClienteTCPBasico {
    public static void main(String[] args) {
        try{
                Socket cliente = new Socket("localhost",12345);
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                Date data_atual = (Date)entrada.readObject();
                JOptionPane.showMessageDialog(null, "Data recebida: do servidor: " + data_atual.toString());
                entrada.close();
                System.out.println("Conexao encerrada");
                }
        catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }
}
