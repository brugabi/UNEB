package chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatCliente extends UnicastRemoteObject implements Chat {

    private String nome;

    protected ChatCliente(String nome) throws RemoteException {
        super();
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    @Override
    public void enviarMsg(String nome, String msg) throws RemoteException {}

    @Override
    public void adicionarCliente(Chat cliente, String nome) throws RemoteException {}

    @Override
    public void receberMsg(String nome, String msg) throws RemoteException {
        if (!nome.equals(this.nome)) { // Aqui é a parte do VOCÊ
            System.out.println(nome + ": " + msg);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String servidorIP = "127.0.0.1";
        try {
            Registry registry = LocateRegistry.getRegistry(servidorIP, 1098);

            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();
            
            Chat stub = (Chat)registry.lookup("Chat.java");
            ChatCliente cliente = new ChatCliente(nome);

            UnicastRemoteObject.unexportObject(cliente, true);
            Chat clienteStub = (Chat) UnicastRemoteObject.exportObject(cliente, 0);
    
            stub.adicionarCliente(clienteStub, nome);

            while (true) {
                String msg = scanner.nextLine();

                // Limpa a linha anterior
                System.out.print("\033[1A\033[2K");
                System.out.flush();
                System.out.println("Você: " + msg);
                stub.enviarMsg(nome, msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}