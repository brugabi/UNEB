import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Q4Server {

    private ArrayList<Q4Item> itens = new ArrayList<>();
    private int port;

    public Q4Server(int port) {
        this.port = port;
    }

    public ArrayList<Q4Item> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Q4Item> itens) {
        this.itens = itens;
    }

    public void addItem (Q4Item item) {
        this.itens.add(item);
    }

    public String dar_lance (String nome) {
        boolean check = false;
        for (Q4Item item : itens) {
            if (nome.equals(item.getNome())) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Item: " + item.getNome());
                System.out.println("Lance atual: " + item.getLance());
                System.out.println("Disponibilidade: " + item.isCheck());

                System.out.println("Informe seu lance: ");
                item.setLance(scanner.nextFloat());

                return "Lance recebido com sucesso";
            }
        }
        return "Nao foi possivel dar o lance";
    }

    public void listar () {
        System.out.println("Itens disponiveis:");
        for (Q4Item item : itens){
            System.out.println(item.getNome());
        }
    }


    public static void main(String[] args) {

        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor esperando conexões...");

            // Criação da lista de objetos
            Q4Server server = new Q4Server(port);
            server.addItem(new Q4Item("suco", true));
            server.addItem(new Q4Item("manga", true));


            while (true) {
                // Aceitar conexão do cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Criar uma nova thread para lidar com o cliente
                Q4ClientHandler clientHandler = new Q4ClientHandler(clientSocket, server.itens);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
