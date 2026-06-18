package roteiro4.parte2;


public class Criptomoeda implements MetodoPagamento, MetodoPagamentoComSaldo {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Pagamento com criptomoeda. Valor: R$" + valor);
    }

    @Override
    public void validarSaldo(){
        System.out.println("Validando saldo disponivel na carteira de criptomoeda");
    }
}
