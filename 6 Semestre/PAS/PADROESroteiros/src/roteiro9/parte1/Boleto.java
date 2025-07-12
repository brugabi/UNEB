package roteiro9.parte1;

public class Boleto {

    protected double valor;
    protected Juros juros;
    protected Desconto desconto;
    protected Multa multa;

    public Boleto(double valor, CalculosFactory factory) {
            this.valor = valor;
            this.juros = factory.criarJuros();
            this.desconto = factory.criarDesconto();
            this.multa = factory.criarMulta();
        }

        public double calcJuros () {
            return this.valor * this.juros.getJuros();
        }

        public double calcDesconto () {
            return this.valor * this.desconto.getDesconto();
        }

        public double calcMulta () {
            return this.valor * this.multa.getMulta();
        }
    }

