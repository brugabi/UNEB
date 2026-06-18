package roteiro8.parte4;

public class SistemaEstoqueAdapterSAP implements ISistemaEstoqueAdapter{

    private final SistemaEstoque sistemaEstoque;

    public SistemaEstoqueAdapterSAP() {
        this.sistemaEstoque = new SistemaEstoque("SAP");
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
