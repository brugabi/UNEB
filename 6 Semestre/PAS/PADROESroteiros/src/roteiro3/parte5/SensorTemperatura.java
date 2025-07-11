package roteiro3.parte5;

public class SensorTemperatura extends Sensor {
    public SensorTemperatura(String nome) {
        super(nome);
    }

    @Override
    public String getUnidade() {
        return "°C";
    }
}
