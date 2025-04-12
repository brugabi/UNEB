package roteiro1.parte3;

import java.util.List;
public class PedidoService {
    private MysqlConnection connection;
    private List<String> itens;
    private CalculadoraPreco calculadora;

    public PedidoService(List<String> itens, MysqlConnection connection) {
        this.itens = itens;
        this.connection = connection;
        this.calculadora = new CalculadoraPreco();
    }

    public void processarPedido() {
        this.connection.connect();
        double total = this.calculadora.calcularTotal(itens);
        System.out.println("Pedido processado. Valor total: R$ " + total);
    }


}
