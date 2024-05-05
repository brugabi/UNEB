package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServidor extends UnicastRemoteObject implements Chat {

    private List<Chat> clientes; 

    public ChatServidor() throws RemoteException {
        super();
        clientes = new ArrayList<>();
    }

    // Sobrescrita dos métodos

    @Override
    public void receberMsg(String nome, String msg) throws RemoteException {}

    @Override
    public void enviarMsg(String nome, String msg) throws RemoteException {

        System.out.println(nome + ": " + msg);

        for (Chat cliente : clientes) {
            cliente.receberMsg(nome, msg);
        }
    }

    @Override
    public void adicionarCliente(Chat cliente, String nome) {
        clientes.add(cliente);

        System.out.println(nome + " entrou no chat!");
        
    }


    public static void main(String[] args) {
        
        try {
            Registry registry = LocateRegistry.createRegistry(1098);
            ChatServidor chatServidor = new ChatServidor();

            UnicastRemoteObject.unexportObject(chatServidor, true);
            Chat stub = (Chat) UnicastRemoteObject.exportObject(chatServidor, 0);

            registry.rebind("Chat", stub);

            System.out.println("Servidor pronto!");

            while (true) {
                
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}