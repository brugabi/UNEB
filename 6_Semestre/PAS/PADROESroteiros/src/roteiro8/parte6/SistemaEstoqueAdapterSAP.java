package roteiro8.parte6;

public class SistemaEstoqueAdapterSAP extends SistemaEstoqueAdapter {

    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterSAP() {
        this.sistemaEstoque = new SistemaEstoque("SAP");
    }

}
