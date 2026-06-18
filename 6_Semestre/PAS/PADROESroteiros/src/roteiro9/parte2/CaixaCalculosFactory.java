package roteiro9.parte2;

public class CaixaCalculosFactory implements CalculosFactory {
    @Override
    public CalculosFinanceiros criarCalculos() {
        return new CalculosCaixa();
    }
}
