package roteiro2.parte1;

import java.util.List;
public class PedidoService {
    private List<String> itens;
    private double total;

    public PedidoService(List<String>itens) {
        this.itens = itens;
        this.total = calcularTotal();
    }

    public double aplicarDesconto(double desconto) {
        if (desconto > 0 && desconto < 0.3) {
            return total - (total * desconto);
        }
        return total;
    }

    private double calcularTotal() {
        double total = 0;
        for (String item : itens) {
            if (item.equals("Camiseta)")) {
                total += 50.0;
            } else if (item.equals("Calça")) {
                total += 100.0;
            }
            else if (item.equals("Jaqueta")) {
                total += 200.0;
        }
    }
        return total;
    }

    public void processarPedido(double desconto) {
        double valorFinal = aplicarDesconto(desconto);
        System.out.println("Pedido processado. Valor total com desconto: R$ " + valorFinal);
    }
}  
