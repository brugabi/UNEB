package roteiro8.parte4;

import roteiro8.parte1.SistemaEstoque;

public class SistemaEstoqueAdapterDELL implements ISistemaEstoqueAdapter {


    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterDELL() {
        this.sistemaEstoque = new SistemaEstoque("DELL");
    }

    @Override
    public void diminuirQuantidadeItem() {
        this.sistemaEstoque.removerItemEstoque();
    }

    @Override
    public void aumentarQuantidadeItem() {
        this.sistemaEstoque.adicionarItemEstoque();
    }
}