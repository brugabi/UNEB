package roteiro9.parte2;

public class CalculosBrasil implements CalculosFinanceiros {

    @Override
    public double calcularJuros(double valor, int vencimento) {
        double juros = 0;
        switch (vencimento) {
            case 10: juros = 0.03; break;
            case 30: juros = 0.05; break;
            case 60: juros = 0.10; break;
            default: throw new IllegalArgumentException("Vencimento inválido para Banco do Brasil");
        }
        return valor * juros;
    }

    @Override
    public double calcularDesconto(double valor, int vencimento) {
        double desconto = 0;
        switch (vencimento) {
            case 10: desconto = 0.05; break;
            case 30: desconto = 0.03; break;
            case 60: desconto = 0.0; break;
            default: throw new IllegalArgumentException("Vencimento inválido para Caixa");
        }
        return valor * desconto;
    }

    @Override
    public double calcularMulta(double valor, int vencimento) {
        double multa = 0;
        switch (vencimento) {
            case 10: multa = 0.03; break;
            case 30: multa = 0.5; break;
            case 60: multa = 0.15; break;
            default: throw new IllegalArgumentException("Vencimento inválido para Caixa");
        }
        return valor * multa;
    }
}