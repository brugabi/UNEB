package roteiro2.parte2;

public class PagamentoCredito extends Pagamento {


    public PagamentoCredito(double valor, Gateway gateway) {
        super(valor, gateway);
    }

    @Override
    public double calcularTaxa(){
        return this.valor*0.05;
    }

    @Override
    public double calcularDesconto(){
        if (this.valor > 300)
        {
            return this.valor*0.02;
        }
        return 0;
    }

}
