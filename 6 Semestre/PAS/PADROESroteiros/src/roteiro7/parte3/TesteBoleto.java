package roteiro7.parte3;

public class TesteBoleto {

    public static void main(String[] args) {

        // 1. Crie a instância da "fábrica" de boletos primeiro.
        BoletoSimpleFactory factory = new BoletoSimpleFactory();

        // 2. Passe a fábrica como argumento para o construtor do BancoCaixa.
        BancoCaixa banco = new BancoCaixa(factory);

        try {
            banco.gerarBoleto(10, 100);
            banco.gerarBoleto(30, 100);
            banco.gerarBoleto(60, 100);
            banco.gerarBoleto(90, 100); // Este vai lançar a exceção
        } catch (Exception e) {
            System.out.println("ERRO CAPTURADO: " + e.getMessage());
        }
    }
}