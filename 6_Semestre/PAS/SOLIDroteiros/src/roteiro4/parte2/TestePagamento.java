package roteiro4.parte2;


public class TestePagamento {
    public static void main(String[] args) {
        System.out.println("\nPAGAMENTO CARTAO DE CREDITO");
        MetodoPagamento cartaoCredito = new CartaoCredito();
        if (cartaoCredito instanceof CartaoCredito) {
            ((MetodoPagamentoComFatura) cartaoCredito).gerarFatura();
        }
        cartaoCredito.processarPagamento(100.0);

        System.out.println("\nPAGAMENTO BOLETO BANCARIO");
        MetodoPagamento boletoBancario = new BoletoBancario();
        boletoBancario.processarPagamento(200.0);

        System.out.println("\nPAGAMENTO CRIPTOMOEDA");
        MetodoPagamento criptomoeda = new Criptomoeda();
        criptomoeda.processarPagamento(300.0);
    }
}
