package roteiro1.parte5;

public class TestePedido {
    public static void main(String[] args) {

        Frete freteComum = new FreteComum();
        Frete freteExpresso = new FreteExpresso();

        // --- Eletrônicos ---
        Pedido pedidoEletronico = new PedidoEletronicos(500.00);
        System.out.println("Pedido de " + pedidoEletronico.getValor() + " em Eletrônicos.");

        // frete comum
        pedidoEletronico.setFreteStrategy(freteComum);
        System.out.println("Valor do Frete: R$ " + pedidoEletronico.calcularFrete());

        // Mudando para frete expresso EM TEMPO DE EXECUÇÃO
        System.out.println("\nCliente mudou de ideia e quer frete expresso!");
        pedidoEletronico.setFreteStrategy(freteExpresso);
        System.out.println("Valor do Frete: R$ " + pedidoEletronico.calcularFrete());

        System.out.println("\n--------------------------------------------\n");

        // --- Pedido de Móveis (Frete Expresso Indisponível) ---
        Pedido pedidoMovel = new PedidoMoveis(1500.00);
        System.out.println("Pedido de " + pedidoMovel.getValor() + " em Móveis.");


        System.out.println("Neste momento, a opção Expresso não está disponível para Móveis.");
        pedidoMovel.setFreteStrategy(freteComum);
        System.out.println("Valor do Frete: R$ " + pedidoMovel.calcularFrete());
    }
}
