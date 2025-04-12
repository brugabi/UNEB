package roteiro1.parte3;

import java.util.List;
public class CalculadoraPreco {
    
    public double calcularTotal(List<String> itens) {
        double total = 0;
        for (String item : itens) {
            if (item.equals("Pizza")) {
                total += 30.0;
            } else if (item.equals("Bebida")) {
                total += 10.0;
            }
        }
        return total;
    }
}
