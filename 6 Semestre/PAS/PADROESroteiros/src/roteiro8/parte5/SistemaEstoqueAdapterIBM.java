package roteiro8.parte5;

public class SistemaEstoqueAdapterIBM extends SistemaEstoqueAdapter {

    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterIBM() {
        this.sistemaEstoque = new SistemaEstoque("IBM");
    }
}