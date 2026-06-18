import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements ChatServer {
    private ArrayList<String> historico = new ArrayList<>();
    private ArrayList<ChatClient> clientes = new ArrayList<>();
    @Override
    public void enviarMsg(String nome, String msg) throws RemoteException {
        historico.add(nome + ":" + msg);
        System.out.println(nome + ":" + msg);
        for (int i = 0; i < clientes.size(); i++) {
            clientes.get(i).receberMsg(nome, msg);
        }
    }

    @Override
    public void adicionarCliente(ChatClient cliente) {
        this.clientes.add(cliente);
    }

    public static void main(String[] args) {
        Server ChatServer = new Server();
        try {
            ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(ChatServer, 0);
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("Chat", stub);
            System.out.println("Server on");
            while (true) {

            }
        }
        catch (Exception e){
            System.out.println("Erro " + e.getMessage() );
        }
    }
}
