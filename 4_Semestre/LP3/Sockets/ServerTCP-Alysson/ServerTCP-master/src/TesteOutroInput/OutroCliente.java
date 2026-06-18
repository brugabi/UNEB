package TesteOutroInput;

public class OutroCliente {
    public static void main(String[] args) {
        Cliente cliente = new Cliente(12345,"localhost");
        cliente.connect();
    }
}
