package roteiro4.parte2;

public class BoletoBancario implements MetodoPagamento, MetodoPagamentoComFatura {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Pagamento com boleto bancário. Valor: R$" + valor);
    }

    @Override
    public void gerarFatura(){
        System.out.println("Fatura gerada para o boleto bancário");
    }

}
