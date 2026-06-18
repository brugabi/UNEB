package roteiro8.parte6;

public class ControladorContabil {
    private SistemaContabilAdapter sistemacontabilAdapter;
    private AdapterFactory factory;

    public ControladorContabil() {
        this.factory = AdapterFactory.getInstance();
        System.out.println("Controlador de Sistema Contabil Criado");
    }

    public void criarSistemaContabilAdapter(String nome){
        this.sistemacontabilAdapter = this.factory.criarSistemaContabilAdapter(nome);
    }

    public void calcularImposto(){
        this.sistemacontabilAdapter.registrarImposto();
    }
}
