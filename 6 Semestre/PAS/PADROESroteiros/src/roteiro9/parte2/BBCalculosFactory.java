package roteiro9.parte2;

public class BBCalculosFactory implements CalculosFactory {
    @Override
    public CalculosFinanceiros criarCalculos() {
        return new CalculosBrasil();
    }
}
