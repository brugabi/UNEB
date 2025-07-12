package roteiro8.parte1;

import roteiro8.parte1.SistemaContabil;

public class SistemaContabilAdapterDELL implements  ISistemaContabilAdapter{
    private SistemaContabil sistemacontabil;

    public SistemaContabilAdapterDELL() {
        this.sistemacontabil = new SistemaContabil("DELL");
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
