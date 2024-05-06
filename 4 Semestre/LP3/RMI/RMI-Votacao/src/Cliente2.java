import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente2 {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Votacao stub = (Votacao) registry.lookup("votacao");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Escolha em quem votar");
            System.out.println(stub.listagem());

            String nome = scanner.nextLine();
            stub.votar(nome);

            System.out.println(stub.listagem());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
