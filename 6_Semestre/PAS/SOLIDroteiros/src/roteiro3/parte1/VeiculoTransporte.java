package roteiro3.parte1;

public class VeiculoTransporte {
    protected double tarifaBase;

    public VeiculoTransporte(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double calcularTarifa() {
        return this.tarifaBase;
    }
}
