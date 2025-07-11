package roteiro1.parte5;

public class PedidoEletronicos extends Pedido {
    private String setor = "Eletrônicos";

    public PedidoEletronicos(double valor) {
        super(valor);
    }

    public String getSetor() {
        return this.setor;
    }
}
