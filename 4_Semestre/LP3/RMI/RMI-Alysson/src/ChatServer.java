import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    public void enviarMsg(String nome,String msg) throws RemoteException;
    public void adicionarCliente(ChatClient cliente) throws RemoteException;
}
