import java.net.*;
import java.io.*;

public class ClienteEnviarArquivo {
    public static void main(String[] args) {

        System.out.println("Cliente iniciado!");

        int porta = 5000;
        String host = "127.0.0.1";

        try {
            Socket cliente = new Socket("26.147.87.5", porta);

            File arquivo = new File("arquivo.txt");
            FileInputStream arquivoEntrada = new FileInputStream(arquivo);
            OutputStream saida = cliente.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesLidos;

            while ((bytesLidos = arquivoEntrada.read(buffer)) != -1) {
                saida.write(buffer, 0, bytesLidos);
            }

            System.out.println("Arquivo enviado");

            cliente.close();
            arquivoEntrada.close();
            arquivoEntrada.close();
            saida.close();

            System.out.println("Conexão encerrada");

        } catch(IOException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch(IllegalArgumentException e) {
            System.out.println("Porta inválida");
        }

    }
}