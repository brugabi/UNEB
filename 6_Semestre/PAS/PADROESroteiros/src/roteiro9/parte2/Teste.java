package roteiro9.parte2;

public class Teste {
    public static void main(String[] args) {
        Banco banco = new Banco();

        // --- Usando a família de regras da CAIXA ---
        System.out.println("--- GERANDO BOLETOS DA CAIXA ---");
        CalculosFactory fabricaCaixa = new CaixaCalculosFactory();
        banco.gerarBoleto(1000, 10, fabricaCaixa);
        banco.gerarBoleto(1000, 30, fabricaCaixa);

        // --- Usando a família de regras do BANCO DO BRASIL ---
        System.out.println("--- GERANDO BOLETOS DO BANCO DO BRASIL ---");
        CalculosFactory fabricaBB = new BBCalculosFactory();
        banco.gerarBoleto(1000, 10, fabricaBB);
        banco.gerarBoleto(1000, 30, fabricaBB);
    }
}
