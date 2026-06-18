package roteiro7.parte3;

public class BoletoBrasil10Dias extends Boleto {

    public BoletoBrasil10Dias(double valor){
        super(valor);
        this.juros = 0.03;
        this.desconto = 0.05;
        this.multa = 0.02;
    }
}
