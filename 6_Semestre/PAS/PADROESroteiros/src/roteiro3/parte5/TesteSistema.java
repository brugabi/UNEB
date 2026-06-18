package roteiro3.parte5;

public class TesteSistema {
    public static void main(String[] args) {

        System.out.println("--- Iniciando Sistema de Monitoramento IoT ---");

        Observer painel = new PainelControle();

        Sensor temperaturaCozinha = new SensorTemperatura("Temperatura da Cozinha");
        Sensor pressaoSala = new SensorPressao("Pressão da Sala de Estar");
        Sensor umidadeQuarto = new SensorUmidade("Umidade do Quarto");

        temperaturaCozinha.registrarObserver(painel);
        pressaoSala.registrarObserver(painel);
        umidadeQuarto.registrarObserver(painel);

        System.out.println("\n--- SIMULANDO LEITURAS NORMAIS ---");
        temperaturaCozinha.setValor(25.5);
        pressaoSala.setValor(98);
        umidadeQuarto.setValor(55);

        System.out.println("\n--- SIMULANDO LEITURAS DE ALERTA ---");
        temperaturaCozinha.setValor(42);
        pressaoSala.setValor(110);
        umidadeQuarto.setValor(25);
    }
}
