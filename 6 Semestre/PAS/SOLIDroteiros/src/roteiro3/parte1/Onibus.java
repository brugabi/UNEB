package roteiro3.parte1;

public class Onibus extends VeiculoTransporte{
    public Onibus(double tarifaBase) {
        super(tarifaBase);
    }

    @Override
    public double calcularTarifa() {
        return this.tarifaBase * 1.2; //20%
    }
}
