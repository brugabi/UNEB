package roteiro2.parte3;

public class PedidoEletronicos extends Pedido {
    private final String setor = "Eletrônicos";

    public PedidoEletronicos(double valor) {
        super(valor);
    }

    @Override
    public String getSetor() {
        return this.setor;
    }

    @Override
    protected double calcularEmbalagemEspecial() {
        System.out.println("Aplicando custo de embalagem para Eletrônicos (anti-estática)...");
        // Ex: Custo de R$ 15.00 para embalagem especial de eletrônicos
        return 15.00;
    }

    @Override
    protected double aplicarDescontoSetor() {
        System.out.println("Aplicando desconto promocional para Eletrônicos...");

        return 20.00;
    }
}