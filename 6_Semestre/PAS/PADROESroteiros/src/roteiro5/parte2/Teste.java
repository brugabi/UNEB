package roteiro5.parte2;

public class Teste {

    public static void main(String[] args) {

        System.out.println("--- Iniciando Simulação da Torre de Controle ---");

        ControladorAereo controlador = ControladorAereo.getInstance();

        // 2. Simulação de voos em sequência
        // O estado inicial permite decolar.
        System.out.println("\n[Voo-101] solicitando para DECOLAR...");
        controlador.solicitarDecolagem(); // Deve ser CONCEDIDA. O estado da pista inverte.

        // O estado agora não permite decolar.
        System.out.println("\n[Voo-202] solicitando para DECOLAR em seguida...");
        controlador.solicitarDecolagem(); // Deve ser NEGADA.

        // O estado agora permite aterrissar.
        System.out.println("\n[Voo-303] solicitando para ATERRISSAR...");
        controlador.solicitarAterrissagem(); // Deve ser CONCEDIDA. O estado da pista inverte novamente.

        // O estado agora não permite aterrissar.
        System.out.println("\n[Voo-404] solicitando para ATERRISSAR em seguida...");
        controlador.solicitarAterrissagem(); // Deve ser NEGADA.

        System.out.println("\n--- Fim da Simulação ---");
    }
}