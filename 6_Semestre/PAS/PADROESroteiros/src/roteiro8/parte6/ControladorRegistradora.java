package roteiro8.parte6;

public class ControladorRegistradora {

    private SistemaContabilAdapter sistemacontabilAdapter;
    private SistemaEstoqueAdapter sistemaestoqueAdapter;
    private AdapterFactory factory;

    public ControladorRegistradora() {
        this.factory = AdapterFactory.getInstance();
        System.out.println("Controlador de Registradora Criado");
    }

    // Método para criar o adaptador contábil com base no nome
    public void criarSistemaContabilAdapter(String nome) {
        this.sistemacontabilAdapter = this.factory.criarSistemaContabilAdapter(nome);
    }

    // Método para criar o adaptador de estoque com base no nome
    public void criarSistemaEstoqueAdapter(String nome) {
        this.sistemaestoqueAdapter = this.factory.criarSistemaEstoqueAdapter(nome);
    }

    // Métodos específicos

    public void registrarVendaSistemaContabil() {
        this.sistemacontabilAdapter.finalizarVenda();
    }

    public void diminuirQuantidadeItem() {
        this.sistemaestoqueAdapter.diminuirQuantidadeItem();
    }
}