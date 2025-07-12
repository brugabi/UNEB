package roteiro9.parte1;

public class TesteBoleto {
    public static void main(String[] args) {

        Banco banco = new Banco();

        System.out.println(" \n --- BANCO CAIXA --- \n ");

        CalculosFactory caixaCalculosFatory = new CaixaCalculosFactory();
        try {
            banco.gerarBoleto(100, caixaCalculosFatory);
            //bancocaixa.gerarBoleto(30, 100);
            //bancocaixa.gerarBoleto(60, 100);
            //bancocaixa.gerarBoleto(90, 100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(" \n --- BANCO DO BRASIL --- \n ");

        CalculosFactory bbCalculosFatory = new BBCalculosFactory();
        try {
            banco.gerarBoleto(100, bbCalculosFatory);
            //bancobrasil.gerarBoleto(30, 100);
            //bancobrasil.gerarBoleto(60, 100);
            //bancobrasil.gerarBoleto(90, 100);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
