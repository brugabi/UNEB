package roteiro7.parte2;

public class BancoCaixa {

    private BoletoSimpleFactory boletoSimpleFactory;

    public BancoCaixa(BoletoSimpleFactory boletoSimpleFactory) {
        this.boletoSimpleFactory = boletoSimpleFactory;
    }


    public Boleto gerarBoleto(int vencimento, double valor){

        Boleto boleto = this.boletoSimpleFactory.criarBoleto(vencimento, valor);

        System.out.println("***********************");
        System.out.println("Boleto gerado com sucesso. Valor = "+ valor);
        System.out.println("Valor Juros = "+ boleto.calcJuros());
        System.out.println("Valor Desconto = "+ boleto.calcDesconto());
        System.out.println("Valor Multa = "+ boleto.calcMulta());
        System.out.println("***********************");

        return boleto;
    }
}
