package roteiro1.parte5;

public abstract class Pedido {
    protected double valor;
    private Frete freteStrategy;
    public Pedido(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    // Setter para injetar/trocar a estratégia em tempo de execução
    public void setFreteStrategy(Frete freteStrategy) {
        this.freteStrategy = freteStrategy;
    }


    public double calcularFrete() {
        if (freteStrategy == null) {
            System.out.println("Nenhum tipo de frete foi selecionado.");
            return 0.0;
        }
        return freteStrategy.calcular(this);
    }
}
