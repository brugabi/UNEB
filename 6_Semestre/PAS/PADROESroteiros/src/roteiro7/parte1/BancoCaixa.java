package roteiro7.parte1;

public class BancoCaixa {

    public BancoCaixa() {

    }

    public Boleto gerarBoleto(int vencimento, double valor){

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

        System.out.println("***********************");
        System.out.println("Boleto gerado com sucesso. Valor = "+ valor);
        System.out.println("Valor Juros = "+ boleto.calcJuros());
        System.out.println("Valor Desconto = "+ boleto.calcDesconto());
        System.out.println("Valor Multa = "+ boleto.calcMulta());
        System.out.println("***********************");

        return boleto;
    }
}
