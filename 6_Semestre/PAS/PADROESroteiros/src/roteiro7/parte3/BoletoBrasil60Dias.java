package roteiro7.parte3;

public class BoletoBrasil60Dias extends Boleto{

    public BoletoBrasil60Dias(double valor){
        super(valor);
        this.juros = 0.10;
        this.desconto = 0.0;
        this.multa = 0.15;
    }
}
