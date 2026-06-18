package roteiro8.parte6;

public class AdapterFactory {

    private SistemaEstoqueAdapter sistemaestoqueAdapter;
    private SistemaContabilAdapter sistemacontabilAdapter;

    private static AdapterFactory instance = new AdapterFactory();

    public static AdapterFactory getInstance(){
        return instance;
    }

    public SistemaContabilAdapter criarSistemaContabilAdapter(String nome){
        if (nome.equals("IBM"))
            this.sistemacontabilAdapter = new SistemaContabilAdapterIBM();
        else if (nome.equals("DELL"))
            this.sistemacontabilAdapter = new SistemaContabilAdapterDELL();
        else if (nome.equals("SAP"))
            this.sistemacontabilAdapter = new SistemaContabilAdapterSAP();

        return this.sistemacontabilAdapter;
    }

    public SistemaEstoqueAdapter criarSistemaEstoqueAdapter(String nome){
        if (nome.equals("IBM"))
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterIBM();
        else if (nome.equals("DELL"))
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterIBM();
        else if (nome.equals("SAP"))
            this.sistemaestoqueAdapter = new SistemaEstoqueAdapterIBM();

        return this.sistemaestoqueAdapter;
    }
}
