package roteiro6.parte2;

public class ServicoReserva {
    private MysqlConnection connection;
    public void criarReserva() {
        this.connection.connect();
        System.out.println("Lógica de negócio para o Serviço de Reserva");
    }
}
