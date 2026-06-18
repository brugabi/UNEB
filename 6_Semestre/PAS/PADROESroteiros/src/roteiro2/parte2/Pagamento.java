package roteiro2.parte2;

public abstract class Pagamento {

    protected double valor;
    protected Gateway gateway;

    public Pagamento(double valor, Gateway gateway){
        this.valor = valor;
        this.gateway = gateway;
    }

    public double calcularTaxa(){
        return 0;
    }
    public abstract double calcularDesconto();

    public boolean realizarCobranca(){
        double taxa = this.calcularTaxa();
        double desconto = this.calcularDesconto();
        double valorFinal = this.valor + taxa - desconto;

        return this.gateway.cobrar(valorFinal);
    }
}
