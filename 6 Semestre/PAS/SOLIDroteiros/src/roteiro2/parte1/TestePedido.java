package roteiro2.parte1;

import roteiro2.parte1.PedidoService;

import java.util.Arrays;

public class TestePedido {

    public static void main(String[] args) {
        PedidoService pedido = new PedidoService(Arrays.asList("Camiseta", "Calça", "Jaqueta"));
        pedido.processarPedido(0.1);
        pedido.processarPedido(0.0);
        pedido.processarPedido(0.4);

    }
    
}
