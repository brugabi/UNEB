package roteiro2.parte3;

import roteiro1.parte5.Pedido;

public interface Frete {
    double calcular(Pedido pedido);

    double calcular(roteiro2.parte3.Pedido pedido);
}
