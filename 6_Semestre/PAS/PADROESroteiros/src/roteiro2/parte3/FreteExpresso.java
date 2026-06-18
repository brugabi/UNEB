package roteiro2.parte3;

import roteiro1.parte5.Pedido;

public abstract class FreteExpresso implements Frete {
    @Override
    public double calcular(Pedido pedido) {

        System.out.println("Calculando com a estratégia de Frete Expresso (10%)...");
        return pedido.getValor() * 0.10;
    }
}
