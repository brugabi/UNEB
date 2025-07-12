package roteiro7.parte3;

public class BoletoBrasil30Dias extends Boleto{

    public BoletoBrasil30Dias(double valor){
        super(valor);
        this.juros = 0.05;
        this.desconto = 0.02;
        this.multa = 0.05;
    }
}
