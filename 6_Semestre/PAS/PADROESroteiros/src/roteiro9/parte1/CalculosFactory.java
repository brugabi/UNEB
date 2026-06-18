package roteiro9.parte1;

public interface CalculosFactory {
    public Juros criarJuros();
    public Desconto criarDesconto();
    public Multa criarMulta();
}
