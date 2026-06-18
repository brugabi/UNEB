package roteiro4.parte5;


public class BoletoBancario extends Pagamento implements PagamentoReembolsavel {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Pagamento com boleto bancário. Valor: R$" + valor);
    }

    @Override
    public void gerarFatura(){
        System.out.println("Fatura gerada para o boleto bancário");
    }

    @Override
    public void processarReembolso(double valor) {
        System.out.println("Reembolso com boleto bancário. Valor: R$" + valor);
    }

}
