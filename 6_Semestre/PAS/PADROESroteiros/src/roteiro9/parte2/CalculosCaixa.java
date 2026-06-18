package roteiro9.parte2;

public class CalculosCaixa implements CalculosFinanceiros {

    @Override
    public double calcularJuros(double valor, int vencimento) {
        double juros = 0;
        switch (vencimento) {
            case 10: juros = 0.02; break;
            case 30: juros = 0.05; break;
            case 60: juros = 0.10; break;
            default: throw new IllegalArgumentException("Vencimento inválido para Caixa");
        }
        return valor * juros;
    }

    @Override
    public double calcularDesconto(double valor, int vencimento) {
        double desconto = 0;
        switch (vencimento) {
            case 10: desconto = 0.1; break;
            case 30: desconto = 0.05; break;
            case 60: desconto = 0.0; break;
            default: throw new IllegalArgumentException("Vencimento inválido para Caixa");
        }
        return valor * desconto;
    }

    @Override
    public double calcularMulta(double valor, int vencimento) {
        double multa = 0;
        switch (vencimento) {
            case 10: multa = 0.05; break;
            case 30: multa = 0.10; break;
            case 60: multa = 0.20; break;
            default: throw new IllegalArgumentException("Vencimento inválido para Caixa");
        }
        return valor * multa;
    }
}