package roteiro2.parte2;

public class PagamentoDebito extends Pagamento {


    public PagamentoDebito(double valor, Gateway gateway) {
        super(valor, gateway);
    }

    @Override
    public double calcularTaxa(){
        return 4;
    }

    @Override
    public double calcularDesconto(){
        return this.valor*0.05;
    }

    @Override
    public boolean realizarCobranca(){
        double taxa = this.calcularTaxa();
        double desconto = this.calcularDesconto();
        double valorFinal = this.valor + taxa - desconto;

        return this.gateway.cobrar(valorFinal);
    }
}
