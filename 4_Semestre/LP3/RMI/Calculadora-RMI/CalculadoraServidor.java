import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraServidor implements Calculadora{

	@Override
	public int soma(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return a + b;
	}

	@Override
	public int subtracao(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return  a - b;
	}

	@Override
	public int multiplicacao(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return a * b;
	}

	@Override
	public double divisao(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return (double) a / b;
	}
	
	public static void main(String[] args) {
        try {
        	CalculadoraServidor server = new CalculadoraServidor();
        	Calculadora stub = (Calculadora) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Calculadora", stub);

            System.out.println("Servidor pronto...");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
