package roteiro4.parte1;

public interface MetodoPagamento {
    void processarPagamento(double valor);
    void gerarFatura();
    void validarSaldo();
}
