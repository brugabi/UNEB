package roteiro9.parte1;

public class BBCalculosFactory implements CalculosFactory {
    @Override
    public Juros criarJuros() {
        return new BrasilJuros();
    }

    @Override
    public Desconto criarDesconto() {
        return new BrasilDesconto();
    }

    @Override
    public Multa criarMulta() {
        return new BrasilMulta();
    }
}
