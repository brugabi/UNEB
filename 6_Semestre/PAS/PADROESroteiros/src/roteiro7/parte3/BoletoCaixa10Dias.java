package roteiro7.parte3;

public class BoletoCaixa10Dias extends Boleto {

    public BoletoCaixa10Dias(double valor) {
        super(valor);
        this.juros = 0.02;
        this.desconto = 0.1;
        this.multa = 0.05;
    }
}
