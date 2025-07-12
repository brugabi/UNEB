package roteiro8.parte4;

public class SistemaContabilAdapterSAP implements ISistemaContabilAdapter{
    private SistemaContabil sistemacontabil;

    public SistemaContabilAdapterSAP() {
        this.sistemacontabil = new SistemaContabil("SAP");
    }

    @Override
    public void finalizarVenda() {
        this.sistemacontabil.registrarVenda();
    }

    @Override
    public void registrarImposto() {
        this.sistemacontabil.calcularImposto();
    }
}
