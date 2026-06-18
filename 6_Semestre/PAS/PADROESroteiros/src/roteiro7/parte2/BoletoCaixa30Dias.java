package roteiro7.parte2;

public class BoletoCaixa30Dias extends Boleto {

    public BoletoCaixa30Dias(double valor) {
        super(valor);
        this.juros = 0.05;
        this.desconto = 0.05;
        this.multa = 0.1;
    }
}
