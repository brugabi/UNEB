package roteiro8.parte3;

public class ControladorRegistradora {

    private ISistemaContabilAdapter sistemacontabilAdapter;
    private ISistemaEstoqueAdapter sistemaestoqueAdapter;

    public ControladorRegistradora() {
        System.out.println("Controlador de Registradora Criado");
    }

    // Método para criar o adaptador contábil com base no nome
    public void criarSistemaContabilAdapter(String nome) {
        if (nome.equals("DELL")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterDELL();
        } else if (nome.equals("IBM")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterIBM();
        }
    }

    // Método para criar o adaptador de estoque com base no nome
    public void criarSistemaEstoqueAdapter(String nome) {
        if (nome.equals("DELL")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterDELL();
        } else if (nome.equals("IBM")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterIBM();
        }
    }

    // Métodos específicos

    public void registrarVendaSistemaContabil() {
        this.sistemacontabilAdapter.finalizarVenda();
    }

    public void diminuirQuantidadeItem() {
        this.sistemaestoqueAdapter.diminuirQuantidadeItem();
    }
}