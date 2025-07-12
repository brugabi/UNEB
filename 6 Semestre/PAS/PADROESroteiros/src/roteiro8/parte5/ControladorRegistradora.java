package roteiro8.parte5;

public class ControladorRegistradora {

    private SistemaContabilAdapter sistemacontabilAdapter;
    private SistemaEstoqueAdapter sistemaestoqueAdapter;

    public ControladorRegistradora() {
        System.out.println("Controlador de Registradora Criado");
    }

    // Método para criar o adaptador contábil com base no nome
    public void criarSistemaContabilAdapter(String nome) {
        if (nome.equals("DELL")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterDELL();
        } else if (nome.equals("IBM")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterIBM();
        } else if (nome.equals("SAP")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterSAP();
        }
    }

    // Método para criar o adaptador de estoque com base no nome
    public void criarSistemaEstoqueAdapter(String nome) {
        if (nome.equals("DELL")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterDELL();
        } else if (nome.equals("IBM")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterIBM();
        } else if (nome.equals("SAP")) {
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterSAP();
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