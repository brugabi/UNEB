package roteiro7.parte2;

public class Boleto {

    protected double valor;
    protected double juros;
    protected double desconto;
    protected double multa;

    public Boleto( double valor) {
        this.valor = valor;
    }

    public double calcJuros(){
        return this.valor * this.juros;
    }

    public double calcDesconto(){
        return this.valor * this.desconto;
    }

    public double calcMulta(){
        return this.valor * this.multa;
    }
}

