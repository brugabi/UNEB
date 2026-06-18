package roteiro2.parte2;

public class DescontoLivre implements RegraDesconto {
    private double desconto;

    public DescontoLivre(double desconto) {
        this.desconto = desconto;
    }

    @Override
    public double calcular(double total) {
        if (desconto > 0 && desconto < 0.3) {
            return total - (total * desconto);
        }
        return total;
    }
    
}
