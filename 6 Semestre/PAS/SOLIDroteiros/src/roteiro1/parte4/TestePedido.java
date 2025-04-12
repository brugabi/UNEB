package roteiro1.parte4;

import java.util.ArrayList;
import java.util.List;
public class TestePedido {
    public static void main(String[] args) {
        List<String> lista = new ArrayList<>();
        lista.add("Pizza");
        lista.add("Bebida");

        PedidoService pedidoService = new PedidoService(lista);
        pedidoService.processarPedido();
    }
}
