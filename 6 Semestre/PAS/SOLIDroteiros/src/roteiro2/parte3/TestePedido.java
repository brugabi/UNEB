package roteiro2.parte3;

import java.util.Arrays;

public class TestePedido {
    public static void main(String[] args) {

        //Desconto Padrao
        RegraDesconto descontoParao = new DescontoPadrao();
        PedidoService pedidoPadrao = new PedidoService(Arrays.asList("Camiseta", "Calça", "Jaqueta"), descontoParao);
        pedidoPadrao.processarPedido();

        //VIP
        RegraDesconto descontoVip = new DescontoVip();
        PedidoService pedidoVIP = new PedidoService(Arrays.asList("Camiseta", "Calça", "Jaqueta"), descontoVip);
        pedidoVIP.processarPedido();
    }

}
