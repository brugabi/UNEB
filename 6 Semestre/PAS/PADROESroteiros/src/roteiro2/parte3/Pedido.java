package roteiro2.parte3;

public abstract class Pedido {
    protected double valor;
    private Frete freteStrategy;

    public Pedido(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    public void setFreteStrategy(Frete freteStrategy) {
        this.freteStrategy = freteStrategy;
    }

    // Parte do Padrão Strategy: delega o cálculo do frete
    public double calcularFrete() {
        if (freteStrategy == null) {
            throw new IllegalStateException("O tipo de frete (estratégia) não foi definido.");
        }
        return freteStrategy.calcular(this);
    }

    // --- Início da implementação do Padrão Template Method ---

    /**
     * TEMPLATE METHOD.
     *
     */
    public final double calcularCustoFinal() {
        System.out.println("--- Iniciando cálculo do Custo Final para " + getSetor() + " ---");
        double custoFrete = calcularFrete(); // 1. Usa a STRATEGY de Frete
        double custoEmbalagem = calcularEmbalagemEspecial(); // 2. Chama o método abstrato (a ser definido pela subclasse)
        double desconto = aplicarDescontoSetor(); // 3. Chama o método "gancho" (hook)

        double valorFinal = this.valor + custoFrete + custoEmbalagem - desconto;
        System.out.printf("Valor Pedido: R$%.2f | Frete: R$%.2f | Embalagem: R$%.2f | Desconto: R$%.2f%n", this.valor, custoFrete, custoEmbalagem, desconto);
        System.out.printf("CUSTO FINAL: R$%.2f%n", valorFinal);
        System.out.println("-----------------------------------------------------");

        return valorFinal;
    }

    public abstract String getSetor();

    /**
     * Etapa abstrata do template.
     */
    protected abstract double calcularEmbalagemEspecial();

    /**
     * Fornece um comportamento padrão (sem desconto), mas permite que as subclasses
     * o sobrescrevam se tiverem uma regra de desconto específica.
     */
    protected double aplicarDescontoSetor() {
        return 0.0;
    }
}