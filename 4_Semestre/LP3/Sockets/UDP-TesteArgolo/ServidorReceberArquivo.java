import java.net.*;
import java.io.*;

public class ServidorReceberArquivo {
    public static void main(String[] args) {
        int porta = 5000;

        System.out.println("Servidor ouvindo na porta " + porta);

        try {
            ServerSocket servidor = new ServerSocket(porta);

            Socket cliente = servidor.accept(); // Aceita a conexão do cliente
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
            
            InputStream entradaCliente = cliente.getInputStream();
            FileOutputStream arquivoRecebido = new FileOutputStream("./recebidos/arquivoRecebido.txt");

            byte[] buffer = new byte[1024];
            int bytesLidos;

            while ((bytesLidos = entradaCliente.read(buffer)) != -1) {
                arquivoRecebido.write(buffer, 0, bytesLidos);
            }

            servidor.close();
            cliente.close();
            entradaCliente.close();
            arquivoRecebido.close();


        } catch(IOException e) {    
            System.out.println("Erro: " + e.getMessage());
        } catch(IllegalArgumentException e) {
            System.out.println("Porta inválida");
        }
    }
}
