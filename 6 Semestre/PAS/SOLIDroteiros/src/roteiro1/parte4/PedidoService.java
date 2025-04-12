package roteiro1.parte4;

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
        int pizzas = 0, bebidas = 0;
        for (String item : itens) {
            if (item.equals("Pizza")) {
                total += 30.0;
                pizzas++;
            } else if (item.equals("Bebida")) {
                total += 10.0;
                bebidas++;
            }
        }
        if (pizzas > 0 && bebidas > 0) {
            total *= 0.85;
        }
        else if (pizzas > 0) {
            total *= 0.90;
        }
        return total;
    }
}
