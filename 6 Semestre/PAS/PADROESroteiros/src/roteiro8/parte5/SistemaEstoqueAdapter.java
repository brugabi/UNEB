package roteiro8.parte5;

public abstract class SistemaEstoqueAdapter {

    private SistemaEstoque sistemaEstoque;

    public void diminuirQuantidadeItem() {
        this.sistemaEstoque.removerItemEstoque();
    }
    public void aumentarQuantidadeItem() {
        this.sistemaEstoque.adicionarItemEstoque();
    }
}
