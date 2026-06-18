package roteiro1.parte1;

import java.util.List;
public class PedidoService {
    private MysqlConnection connection;
    private List<String> itens;

    public PedidoService(List<String> itens) {
        this.itens = itens;
        this.connection = new MysqlConnection();
    }

    public void processarPedido() {
        this.connection.connect();
        double total = calcularTotal();
        System.out.println("Pedido processado. Valor total: R$ " + total);
    }

    private double calcularTotal() {
        double total = 0;
        for (String item : itens) {
            if (item.equals("Pizza")) {
                total += 30.0;
            } else if (item.equals("Bebida")) {
                total += 10.0;
            }
        }
        return total;
    }
}
