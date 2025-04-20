package roteiro3.parte4;

public abstract class VeiculoTransporte {
    private double distancia;

    public VeiculoTransporte(double distancia) {
        this.distancia = distancia;
    }

    public double getDistancia() {
        return this.distancia;
    }

    public abstract double calcularTarifa();
}
