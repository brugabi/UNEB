package roteiro4.parte5;

public abstract class Pagamento {
    public abstract void processarPagamento(double valor);

   public void gerarFatura() {
       System.out.println("Esse metodo nao possui fatura");
   }

    public void validarSaldo() {
        throw new UnsupportedOperationException("Esse metodo nao precisa validar saldo");
    }
}
