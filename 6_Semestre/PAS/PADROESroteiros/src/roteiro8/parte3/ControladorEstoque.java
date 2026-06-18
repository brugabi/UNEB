package roteiro8.parte3;

public class ControladorEstoque {

    private ISistemaEstoqueAdapter sistemaestoqueAdapter;

    public ControladorEstoque() {
        System.out.println("Controlador de Sistema de Estoque Criado");
    }

    public void criarSistemaEstoqueAdapter(String nome) {
        if (nome.equals("DELL")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterDELL();
        } else if (nome.equals("IBM")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterIBM();
        }

    }

    public void aumentarQuantidadeItem() {
        this.sistemaestoqueAdapter.aumentarQuantidadeItem();
    }

    public void diminuirQuantidadeItem() {
        this.sistemaestoqueAdapter.diminuirQuantidadeItem();
    }
}