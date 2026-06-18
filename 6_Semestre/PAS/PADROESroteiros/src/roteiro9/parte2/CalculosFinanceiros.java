package roteiro9.parte2; // Novo pacote

public interface CalculosFinanceiros {
    // Os métodos agora recebem o valor e o vencimento para fazer o cálculo
    double calcularJuros(double valor, int vencimento);
    double calcularDesconto(double valor, int vencimento);
    double calcularMulta(double valor, int vencimento);
}