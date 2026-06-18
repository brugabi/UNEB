package roteiro9.parte2;

public class Banco {
    // O método agora só precisa da fábrica
    public Boleto gerarBoleto(double valor, int vencimento, CalculosFactory fabrica) {
        // 1. Usa a fábrica para obter o conjunto de regras do banco correto
        CalculosFinanceiros regras = fabrica.criarCalculos();

        // 2. Cria o boleto passando o conjunto de regras
        Boleto boleto = new Boleto(valor, vencimento, regras);

        System.out.println("***********************");
        System.out.println("Boleto gerado com sucesso para " + vencimento + " dias. Valor = " + valor);
        System.out.println("Valor Juros = " + String.format("%.2f", boleto.calcJuros()));
        System.out.println("Valor Desconto = " + String.format("%.2f", boleto.calcDesconto()));
        System.out.println("Valor Multa = " + String.format("%.2f", boleto.calcMulta()));
        System.out.println("***********************\n");

        return boleto;
    }
}