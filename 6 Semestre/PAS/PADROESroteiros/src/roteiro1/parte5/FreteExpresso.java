package roteiro1.parte5;

public class FreteExpresso implements Frete {
    @Override
    public double calcular(Pedido pedido) {

        System.out.println("Calculando com a estratégia de Frete Expresso (10%)...");
        return pedido.getValor() * 0.10;
    }
}
