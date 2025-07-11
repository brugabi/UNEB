package roteiro3.parte5;

public class SensorUmidade extends Sensor {
    public SensorUmidade(String nome) {
        super(nome);
    }

    @Override
    public String getUnidade() {
        return "%";
    }
}
