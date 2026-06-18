package roteiro1.parte5;

public class PedidoMoveis extends Pedido {
    private String setor = "Móveis";

    public PedidoMoveis(double valor) {
        super(valor);
    }

    public String getSetor() {
        return this.setor;
    }
}
