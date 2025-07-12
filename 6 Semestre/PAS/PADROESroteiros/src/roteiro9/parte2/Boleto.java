package roteiro9.parte2;

public class Boleto {
    protected double valor;
    protected int vencimento;
    protected CalculosFinanceiros calculos; // Contém o conjunto de regras

    public Boleto(double valor, int vencimento, CalculosFinanceiros calculos) {
        this.valor = valor;
        this.vencimento = vencimento;
        this.calculos = calculos;
    }

    public double calcJuros() {
        return this.calculos.calcularJuros(this.valor, this.vencimento);
    }

    public double calcDesconto() {
        return this.calculos.calcularDesconto(this.valor, this.vencimento);
    }

    public double calcMulta() {
        return this.calculos.calcularMulta(this.valor, this.vencimento);
    }
}