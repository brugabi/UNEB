import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class ClienteTCPBasico {
    
    public static void main(String[] args) {
        Socket cliente = null;
        try {
            int porta = 12345;
            cliente = new Socket("localhost", porta);

            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter saidaServidor = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader escolhaDoUsuario = new BufferedReader(new InputStreamReader(System.in));
            
            // Thread para ler mensagens do servidor
            Thread threadLeituraServidor = new Thread(() -> {
                try {
                    String respostaServidor;
                
                    while ((respostaServidor = entradaServidor.readLine()) != null) {
                        System.out.println(respostaServidor);
                    }

                } catch(java.net.SocketException e) {
                    
                    // Socket fechado, interrompe a thread
                    Thread.currentThread().interrupt();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });

            // Inicia a thread de leitura do servidor
            threadLeituraServidor.start();

            // Lendo resposta do usuário e enviando para o servidor
            String escolhaUsuario;
            while ((escolhaUsuario = escolhaDoUsuario.readLine()) != null) {
                saidaServidor.println(escolhaUsuario);
                if (escolhaUsuario.equals("7")) {
                    break;
                }
            }

            System.out.println("Conexão encerrada.");
            threadLeituraServidor.interrupt();
            cliente.close();
            entradaServidor.close();
            saidaServidor.close();
            escolhaDoUsuario.close();
            
        } catch(IOException e) {
            System.out.println("Erro IOException: " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            try {
                if (cliente != null) {
                    cliente.close();
                }
            } catch (Exception e) {
                System.out.println("Erro ao fechar o cliente: " + e.getMessage());
            }
        }
    }
}