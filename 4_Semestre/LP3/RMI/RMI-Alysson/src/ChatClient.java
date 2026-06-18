import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    public void receberMsg(String nome, String msg) throws RemoteException;
}
