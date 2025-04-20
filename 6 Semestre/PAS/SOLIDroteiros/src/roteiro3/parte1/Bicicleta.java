package roteiro3.parte1;

public class Bicicleta extends VeiculoTransporte {

    public Bicicleta() {
        super(0);
    }

    @Override
    public double calcularTarifa() {
        throw new UnsupportedOperationException("Bicicleta não possui tarifa");
    }
}
