import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteTCPBasico {
    public static void main(String[] args) {

        try{
                Socket cliente = new Socket("localhost",12345);
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                Scanner alysson = new Scanner(System.in);
                String mensagem = alysson.nextLine();
                saida.writeObject(mensagem);
                entrada.close();
                saida.close();
                System.out.println("Conexao encerrada");
                }
        catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }
}
