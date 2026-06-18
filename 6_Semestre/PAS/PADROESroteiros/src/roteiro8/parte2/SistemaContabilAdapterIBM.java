package roteiro8.parte2;

public class SistemaContabilAdapterIBM implements ISistemaContabilAdapter {
    private SistemaContabil sistemacontabil;

    public SistemaContabilAdapterIBM() {
        this.sistemacontabil = new SistemaContabil("IBM");
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
