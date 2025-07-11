package roteiro2.parte2;

public class TestePagamento {

    public static void main(String[] args) {

        Gateway gateway = new Gateway();

        double valor = 1000;

        PagamentoCredito pagtoCredito = new PagamentoCredito(valor, gateway);
        pagtoCredito.realizarCobranca();
        System.out.println("*****************************\n");

        PagamentoDebito pagtoDebito = new PagamentoDebito(valor, gateway);
        pagtoDebito.realizarCobranca();
        System.out.println("*****************************\n");

        PagamentoDinheiro pagtoDinheiro = new PagamentoDinheiro(valor, gateway);
        pagtoDinheiro.realizarCobranca();
    }
}
