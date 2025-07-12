package roteiro8.parte6;

import roteiro8.parte1.SistemaEstoque;

public class SistemaEstoqueAdapterDELL extends SistemaEstoqueAdapter {

    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterDELL() {
        this.sistemaEstoque = new SistemaEstoque("DELL");
    }

}