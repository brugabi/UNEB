package roteiro6.parte2;

public class RelatorioReserva {
    private MysqlConnection connection;

    public void gerarRelatorio() {
        this.connection.connect();
        System.out.println("Lógica de negócio para o Relatório de Reserva");
    }
}
