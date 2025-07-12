package roteiro8.parte6;

public class SistemaContabilAdapterSAP extends SistemaContabilAdapter {
    private SistemaContabil sistemacontabil;

    public SistemaContabilAdapterSAP() {
        this.sistemacontabil = new SistemaContabil("SAP");
    }

}
