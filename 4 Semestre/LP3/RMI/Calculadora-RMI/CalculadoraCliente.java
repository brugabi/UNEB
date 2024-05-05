import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculadoraCliente {
	public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Calculadora stub = (Calculadora) registry.lookup("Calculadora");

            int a = 10;
            int b = 5;

            System.out.println("Resultado da adição: " + stub.soma(a, b));
            System.out.println("Resultado da subtração: " + stub.subtracao(a, b));
            System.out.println("Resultado da multiplicação: " + stub.multiplicacao(a, b));
            System.out.println("Resultado da divisão: " + stub.divisao(a, b));
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
