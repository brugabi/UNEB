package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
    
    void enviarMsg(String nome, String msg) throws RemoteException;
    void receberMsg(String nome, String msg) throws RemoteException;
    void adicionarCliente(Chat cliente, String nome) throws RemoteException;
}
