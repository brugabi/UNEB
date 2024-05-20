import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Q4ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<Q4Item> listaDeObjetos;

    public Q4ClientHandler(Socket clientSocket, List<Q4Item> listaDeObjetos) {
        this.clientSocket = clientSocket;
        this.listaDeObjetos = listaDeObjetos;
    }

    @Override
    public void run() {
        try {
            // Enviar a lista de objetos para o cliente
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(listaDeObjetos);

            objectOutputStream.close();
            clientSocket.close();
            System.out.println("Lista de objetos enviada para o cliente " + clientSocket.getInetAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
