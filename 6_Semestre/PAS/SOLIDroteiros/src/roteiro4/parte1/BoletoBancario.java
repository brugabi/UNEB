package roteiro4.parte1;

public class BoletoBancario implements MetodoPagamento {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Pagamento com boleto bancário. Valor: R$" + valor);
    }

    @Override
    public void gerarFatura(){
        System.out.println("Fatura gerada para o boleto bancário");
    }

    @Override
    public void validarSaldo(){
        throw new UnsupportedOperationException("Boletos nao precisam validar saldo");
    }
}
