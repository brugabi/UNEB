package roteiro2.parte3;

public class PedidoMoveis extends Pedido {
    private final String setor = "Móveis";

    public PedidoMoveis(double valor) {
        super(valor);
    }

    @Override
    public String getSetor() {
        return this.setor;
    }

    @Override
    protected double calcularEmbalagemEspecial() {
        System.out.println("Aplicando custo de embalagem para Móveis (item grande)...");

        return this.valor * 0.05;
    }

}