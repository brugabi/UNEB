package roteiro9.parte1;

public class CaixaCalculosFactory implements CalculosFactory {
    @Override
    public Juros criarJuros() {
        return new CaixaJuros();
    }

    @Override
    public Desconto criarDesconto() {
        return new CaixaDesconto();
    }

    @Override
    public Multa criarMulta() {
        return new CaixaMulta();
    }
}
