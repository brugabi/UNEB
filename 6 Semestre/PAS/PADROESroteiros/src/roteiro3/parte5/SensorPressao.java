package roteiro3.parte5;

public class SensorPressao extends Sensor {
    public SensorPressao(String nome) {
        super(nome);
    }

    @Override
    public String getUnidade() {
        return " Pa";
    }
}
