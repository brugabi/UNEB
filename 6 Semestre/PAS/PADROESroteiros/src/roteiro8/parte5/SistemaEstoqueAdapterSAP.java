package roteiro8.parte5;

public class SistemaEstoqueAdapterSAP extends SistemaEstoqueAdapter {

    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterSAP() {
        this.sistemaEstoque = new SistemaEstoque("SAP");
    }

}
