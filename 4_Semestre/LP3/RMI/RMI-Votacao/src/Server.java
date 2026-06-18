import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements Votacao {
    private ArrayList<Candidato> candidatos = new ArrayList<>();


    @Override
    public String listagem() throws RemoteException {
        for (Candidato candidato : candidatos) {
            System.out.println(candidato.getNome());
        }

        return this.candidatos.toString();

    }

    @Override
    public void votar(String nome) throws RemoteException {
        for (Candidato candidato : candidatos) {
            if (nome.equals(candidato.getNome())){
                candidato.setVotos();
            }
        }
    }

    public void setCandidatos(Candidato candidato) {
        this.candidatos.add(candidato);
    }

    public static void main(String[] args) {
        Candidato candidato1 = new Candidato("alysson");
        Candidato candidato2 = new Candidato("argolo");
        Server server = new Server();
        server.setCandidatos(candidato1);
        server.setCandidatos(candidato2);

        try {
            Votacao stub = (Votacao) UnicastRemoteObject.exportObject(server,0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("votacao", stub);

            System.out.println("Servidor funcionando...");
            server.listagem();
            stub.listagem();
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }
}
