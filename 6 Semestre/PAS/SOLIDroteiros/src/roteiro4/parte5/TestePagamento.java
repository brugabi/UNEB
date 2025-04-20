package roteiro4.parte5;

public class TestePagamento {
    public static void main(String[] args) {
        System.out.println("\nPAGAMENTO CARTAO DE CREDITO");
        Pagamento cartaoCredito = new CartaoCredito();
        cartaoCredito.gerarFatura();
        cartaoCredito.processarPagamento(100.0);
        if (cartaoCredito instanceof PagamentoReembolsavel) {
            ((PagamentoReembolsavel) cartaoCredito).processarReembolso(50.0);
        }

        System.out.println("\nPAGAMENTO BOLETO BANCARIO");
        Pagamento boletoBancario = new BoletoBancario();
        boletoBancario.gerarFatura();
        boletoBancario.processarPagamento(200.0);
        if (boletoBancario instanceof PagamentoReembolsavel) {
            ((PagamentoReembolsavel) boletoBancario).processarReembolso(100.0);
        }

        System.out.println("\nPAGAMENTO CRIPTOMOEDA");
        Pagamento criptomoeda = new Criptomoeda();
        criptomoeda.gerarFatura();
        criptomoeda.processarPagamento(300.0);
        if (criptomoeda instanceof PagamentoReembolsavel) {
            ((PagamentoReembolsavel) criptomoeda).processarReembolso(150.0);
        }
    }
}