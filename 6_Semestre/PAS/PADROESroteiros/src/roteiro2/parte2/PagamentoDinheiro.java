package roteiro2.parte2;

public class PagamentoDinheiro extends Pagamento{

    public PagamentoDinheiro(double valor, Gateway gateway) {
        super(valor, gateway);
    }

    @Override
    public double calcularTaxa(){
        return 0;
    }

    @Override
    public double calcularDesconto(){
        return this.valor*0.1;
    }

    @Override
    public boolean realizarCobranca(){
        double taxa = this.calcularTaxa();
        double desconto = this.calcularDesconto();
        double valorFinal = this.valor + taxa - desconto;

        return this.gateway.cobrar(valorFinal);
    }
}
