import java.rmi.*;

public interface Calculadora extends Remote {
	int soma(int a, int b) throws RemoteException;
	int subtracao(int a, int b) throws RemoteException;
	int multiplicacao(int a, int b) throws RemoteException;
	double divisao(int a, int b) throws RemoteException;
}
