package roteiro7.parte1;

public class BoletoCaixa60Dias extends Boleto {

    public BoletoCaixa60Dias(double valor) {
        super(valor);
        this.juros = 0.1;
        this.desconto = 0;
        this.multa = 0.2;
    }
}
