package roteiro1.parte5;

public class FreteComum implements Frete {
    @Override
    public double calcular(Pedido pedido) {

        System.out.println("Calculando com a estratégia de Frete Comum (5%)...");
        return pedido.getValor() * 0.05;
    }
}
