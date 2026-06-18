package roteiro2.parte2;

import java.util.Arrays;

public class TestePedido {
    public static void main(String[] args) {
        RegraDesconto desconto = new DescontoLivre(0.2);
        PedidoService pedido = new PedidoService(Arrays.asList("Camiseta", "Calça", "Jaqueta"), desconto);

        pedido.processarPedido(0.0);
    }

}
