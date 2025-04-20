package roteiro3.parte3;

public class Bicicleta extends VeiculoTransporte {

    public Bicicleta(double distancia) {
        super(distancia);
    }

    @Override
    public double calcularTarifa() {
        return 0;
    }

   @Override
    public double calcularTotalCombustivel(double consumoPorKm) {
        return 0;
    }
}
