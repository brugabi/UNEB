package roteiro2.parte1;

public class PagamentoCredito {

    private double valor;
    private Gateway gateway;

    public PagamentoCredito(double valor, Gateway gateway){
        this.valor = valor;
        this.gateway = gateway;
    }

    public double calcularTaxa(){
        return this.valor*0.05;
    }

    public double calcularDesconto(){
        if (this.valor > 300)
        {
            return this.valor*0.02;
        }
        return 0;
    }

    public boolean realizarCobranca(){
        double taxa = this.calcularTaxa();
        double desconto = this.calcularDesconto();
        double valorFinal = this.valor + taxa - desconto;

        return this.gateway.cobrar(valorFinal);
    }
}
