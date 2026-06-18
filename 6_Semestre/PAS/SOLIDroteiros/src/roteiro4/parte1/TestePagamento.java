package roteiro4.parte1;

public class TestePagamento {
    public static void main(String[] args) {
        System.out.println("\nPAGAMENTO CARTAO DE CREDITO");
        MetodoPagamento cartaoCredito = new CartaoCredito();
        cartaoCredito.gerarFatura();
        cartaoCredito.processarPagamento(100.0);

        System.out.println("\nPAGAMENTO BOLETO BANCARIO");
        MetodoPagamento boletoBancario = new BoletoBancario();
        boletoBancario.gerarFatura();
        boletoBancario.processarPagamento(200.0);

        System.out.println("\nPAGAMENTO CRIPTOMOEDA");
        MetodoPagamento criptomoeda = new Criptomoeda();
        criptomoeda.gerarFatura();
        criptomoeda.processarPagamento(300.0);
    }
}
