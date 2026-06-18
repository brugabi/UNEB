package roteiro6.parte3;

public class RelatorioReserva {
    private Connection connection;

    public void gerarRelatorio() {
        this.connection.connect();
        System.out.println("Lógica de negócio para o Relatório de Reserva");
    }
}
