import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements Chat {


    protected Servidor() throws RemoteException {
        super();
    }

    @Override
    public String digaola() throws RemoteException {
        return "Ola, mundo";
    }

    public static void main(String[] args) throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("remi://localhost:1099/Servidor", new Servidor());
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}