package roteiro8.parte4;

public class SistemaEstoqueAdapterIBM implements ISistemaEstoqueAdapter {

    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterIBM() {
        this.sistemaEstoque = new SistemaEstoque("IBM");
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