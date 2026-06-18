package roteiro2.parte1;

public class PagamentoDebito {

    private double valor;
    private Gateway gateway;

    public PagamentoDebito(double valor, Gateway gateway){
        this.valor = valor;
        this.gateway = gateway;
    }

    public double calcularTaxa(){
        return 4;
    }

    public double calcularDesconto(){
        return this.valor*0.05;
    }

    public boolean realizarCobranca(){
        double taxa = this.calcularTaxa();
        double desconto = this.calcularDesconto();
        double valorFinal = this.valor + taxa - desconto;

        return this.gateway.cobrar(valorFinal);
    }
}
