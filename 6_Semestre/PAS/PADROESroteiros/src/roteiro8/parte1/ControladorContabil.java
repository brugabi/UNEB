package roteiro8.parte1;

public class ControladorContabil {
    private ISistemaContabilAdapter sistemacontabilAdapter;

    public ControladorContabil() {
        System.out.println("Controlador de Sistema Contabil Criado");
    }

    public void criarSistemaContabilAdapter(String nome){
        if (nome.equals("IBM"))
            this.sistemacontabilAdapter = new SistemaContabilAdapterIBM();
        else if (nome.equals("DELL"))
            this.sistemacontabilAdapter = new SistemaContabilAdapterDELL();
    }

    public void calcularImposto(){
        this.sistemacontabilAdapter.registrarImposto();
    }
}
