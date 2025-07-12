package roteiro9.parte1;

public  class Banco {

    public Boleto gerarBoleto(double valor, CalculosFactory factory) {

        Boleto boleto = new Boleto (valor, factory);

        System.out.println("***********************");
        System.out.println("Boleto gerado com sucesso. Valor = "+ valor);
        System.out.println("Valor Juros = "+ boleto.calcJuros());
        System.out.println("Valor Desconto = "+ boleto.calcDesconto());
        System.out.println("Valor Multa = "+ boleto.calcMulta());
        System.out.println("***********************");

        return boleto;
    }

}
