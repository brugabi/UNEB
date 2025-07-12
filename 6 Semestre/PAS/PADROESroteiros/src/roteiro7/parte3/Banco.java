package roteiro7.parte3;

public abstract class Banco {

    public Boleto gerarBoleto(int vencimento, double valor){

        Boleto boleto = this.criarBoleto(vencimento, valor);

        System.out.println("***********************");
        System.out.println("Boleto gerado com sucesso. Valor = "+ valor);
        System.out.println("Valor Juros = "+ boleto.calcJuros());
        System.out.println("Valor Desconto = "+ boleto.calcDesconto());
        System.out.println("Valor Multa = "+ boleto.calcMulta());
        System.out.println("***********************");

        return boleto;
    }

    public abstract Boleto criarBoleto(int vencimento, double valor);
}
