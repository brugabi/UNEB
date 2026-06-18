package Threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
        public static void main(String[] args) {
            try {
                String msg = "";

                Socket cliente = new Socket("localhost", 12345);
                Scanner in = new Scanner(System.in);
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                for (int i = 0; i < 3; i++) {
                    System.out.println("Mensagem recebida: " + entrada.readObject());
                    msg = in.nextLine();
                    saida.writeObject(msg);
                }



                saida.close();
                entrada.close();
                System.out.println("Conexão Encerrada");
            }
            catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }



