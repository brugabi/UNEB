package roteiro8.parte5;

public class SistemaContabilAdapterIBM extends SistemaContabilAdapter {
    private SistemaContabil sistemacontabil;

    public SistemaContabilAdapterIBM() {
        this.sistemacontabil = new SistemaContabil("IBM");
    }

}
