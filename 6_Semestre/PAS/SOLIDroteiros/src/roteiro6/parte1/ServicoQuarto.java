package roteiro6.parte1;

public class ServicoQuarto {
    private MysqlConnection connection;

    public ServicoQuarto() {
        this.connection = new MysqlConnection();
    }

    public void verificarQuarto(){
        this.connection.connect();
        System.out.println("Lógica de negócio para o Serviço de Quarto");
    }
}
