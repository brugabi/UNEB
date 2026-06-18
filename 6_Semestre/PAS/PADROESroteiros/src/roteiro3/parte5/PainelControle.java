package roteiro3.parte5;

public class PainelControle implements Observer{

    @Override
    public void update(Sensor sensor) {
        if (sensor instanceof SensorTemperatura) {
            if (sensor.getValor() > 40) {
                System.out.println("!! ALERTA DE TEMPERATURA !! Sensor '" + sensor.getNome() + "' atingiu valor crítico: " + sensor.getValor() + sensor.getUnidade());
            }
        } else if (sensor instanceof SensorPressao) {
            if (sensor.getValor() > 100) {
                System.out.println("!! ALERTA DE PRESSÃO !! Sensor '" + sensor.getNome() + "' atingiu valor crítico: " + sensor.getValor() + sensor.getUnidade());
            }
        } else if (sensor instanceof SensorUmidade) {
            if (sensor.getValor() < 30) {
                System.out.println("!! ALERTA DE UMIDADE !! Sensor '" + sensor.getNome() + "' atingiu valor crítico: " + sensor.getValor() + sensor.getUnidade());
            }
        } else {
            System.out.println("INFO PAINEL: Leitura normal recebida do sensor '" + sensor.getNome() + "'");
        }
    }
}
