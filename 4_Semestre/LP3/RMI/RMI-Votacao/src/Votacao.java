import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Votacao extends Remote {
    String listagem() throws RemoteException;
    void votar (String nome) throws RemoteException;
}
