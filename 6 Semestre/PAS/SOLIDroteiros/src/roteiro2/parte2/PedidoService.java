package roteiro2.parte2;

import java.util.List;
public class PedidoService {
    private List<String> itens;
    private double total;
    private RegraDesconto regraDesconto;

    public PedidoService(List<String> itens, RegraDesconto regraDesconto) {
        this.regraDesconto = regraDesconto;
        this.itens = itens;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        double total = 0;
        for (String item : itens) {
            if (item.equals("Camiseta")) {
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
        double valorFinal = regraDesconto.calcular(total);
        System.out.println("Pedido processado. Valor total com desconto: R$ " + valorFinal);
    }
}  
