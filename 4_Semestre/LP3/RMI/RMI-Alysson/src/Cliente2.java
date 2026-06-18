import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Cliente2 implements ChatClient{
    private String nome;

    public Cliente2(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try{
            Registry registro = LocateRegistry.getRegistry("localhost");
            ChatServer stub = (ChatServer) registro.lookup("Chat");
            System.out.println("Qual o seu nome?");
            Cliente cliente2 = new Cliente(in.nextLine());
            ChatClient clienteStub = (ChatClient) UnicastRemoteObject.exportObject(cliente2, 0);

            stub.adicionarCliente(clienteStub);

            while (true){
                stub.enviarMsg(cliente2.getNome(), in.nextLine());
                System.out.print("\033[1A\033[2K");
            }
        }
        catch (Exception e){
            System.out.println("Erro cliente :" + e.getMessage());
        }
    }

    @Override
    public void receberMsg(String nome, String msg) throws RemoteException {
        System.out.println(nome + ":" + msg);
    }

}
