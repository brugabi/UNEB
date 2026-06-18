package roteiro4.parte1;

public class Criptomoeda implements MetodoPagamento {
    @Override
    public void processarPagamento(double valor) {
        System.out.println("Pagamento com criptomoeda. Valor: R$" + valor);
    }

    @Override
    public void gerarFatura(){
        throw new UnsupportedOperationException("Criptomoeda nao possui fatura");
    }

    @Override
    public void validarSaldo(){
        System.out.println("Validando saldo disponivel na carteira de criptomoeda");
    }
}
