package roteiro8.parte1;

import roteiro8.parte1.SistemaContabil;

public class SistemaContabilAdapterIBM implements ISistemaContabilAdapter{
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
