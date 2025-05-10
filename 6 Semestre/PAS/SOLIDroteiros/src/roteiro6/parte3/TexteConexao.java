package roteiro6.parte3;

public class TexteConexao {
    public static void main(String[] args) {
        Connection connection = new SqlServer();
        ServicoQuarto quarto = new ServicoQuarto(connection);
        quarto.verificarQuarto();

        ServicoReserva reserva = new ServicoReserva();
        reserva.criarReserva();

        RelatorioReserva relatorio = new RelatorioReserva();
        relatorio.gerarRelatorio();
    }
}
