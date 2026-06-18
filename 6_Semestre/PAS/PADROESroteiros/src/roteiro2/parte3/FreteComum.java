package roteiro2.parte3;

import roteiro1.parte5.Pedido;

public abstract class FreteComum implements Frete {
    @Override
    public double calcular(Pedido pedido) {

        System.out.println("Calculando com a estratégia de Frete Comum (5%)...");
        return pedido.getValor() * 0.05;
    }
}
