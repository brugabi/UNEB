package roteiro8.parte5;

public class ControladorContabil {
    private SistemaContabilAdapter sistemacontabilAdapter;

    public ControladorContabil() {
        System.out.println("Controlador de Sistema Contabil Criado");
    }

    public void criarSistemaContabilAdapter(String nome){
        if (nome.equals("IBM")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterIBM();
        } else if (nome.equals("DELL")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterDELL();
        } else if (nome.equals("SAP")) {
            this.sistemacontabilAdapter = new SistemaContabilAdapterSAP();
        }
    }

    public void calcularImposto(){
        this.sistemacontabilAdapter.registrarImposto();
    }
}
