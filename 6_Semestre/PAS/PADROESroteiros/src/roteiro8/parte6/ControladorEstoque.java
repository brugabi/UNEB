package roteiro8.parte6;

public class ControladorEstoque {

    private SistemaEstoqueAdapter sistemaestoqueAdapter;
    private AdapterFactory factory;

    public ControladorEstoque() {
        this.factory = AdapterFactory.getInstance();
        System.out.println("Controlador de Sistema de Estoque Criado");
    }

    public void criarSistemaEstoqueAdapter(String nome) {
        this.sistemaestoqueAdapter = this.factory.criarSistemaEstoqueAdapter(nome);

    }

    public void aumentarQuantidadeItem() {
        this.sistemaestoqueAdapter.aumentarQuantidadeItem();
    }

    public void diminuirQuantidadeItem() {
        this.sistemaestoqueAdapter.diminuirQuantidadeItem();
    }
}