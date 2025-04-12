package roteiro1.parte5;

import java.util.List;
public class CalculadoraPreco {
    
    public double calcularTotal(List<String> itens) {
        double total = 0;
        int pizzas = 0, bebidas = 0;
        for (String item : itens) {
            if (item.equals("Pizza")) {
                total += 30.0;
                pizzas++;
            } else if (item.equals("Bebida")) {
                total += 10.0;
                bebidas++;
            }
        }

        if (pizzas > 0 && bebidas > 0) {
            total *= 0.85;
        } else if (pizzas > 0) {
            total *= 0.90;
        }

        return total;
    }
    

}
