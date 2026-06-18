package roteiro4.parte2;

public class CartaoCredito implements MetodoPagamento, MetodoPagamentoComFatura, MetodoPagamentoComSaldo {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Pagamento com cartão de crédito. Valor: R$" + valor);
    }

    @Override
    public void gerarFatura(){
        System.out.println("Fatura gerada para o cartão de crédito");
    }
    @Override
    public void validarSaldo(){
        System.out.println("Validando saldo disponível no cartão de crédito");
    }
}
