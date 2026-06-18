package roteiro4.parte4;

public abstract class Pagamento {
    public abstract void processarPagamento(double valor);

    public abstract void gerarFatura();

    public void validarSaldo() {
        throw new UnsupportedOperationException("Esse metodo nao precisa validar saldo");
    }
}
