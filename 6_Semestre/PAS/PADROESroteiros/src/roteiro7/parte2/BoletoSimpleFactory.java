package roteiro7.parte2;

public class BoletoSimpleFactory {

    public Boleto criarBoleto(int vencimento, double valor){

        Boleto boleto = null;
        switch (vencimento) {
            case 10: boleto = new BoletoCaixa10Dias(valor);
                break;
            case 30: boleto = new BoletoCaixa30Dias(valor);
                break;
            case 60: boleto = new BoletoCaixa60Dias(valor);
                break;
            default:
                throw new UnsupportedOperationException("ERRO: Vencimento indisponível");
        }

        return boleto;
    }
}
