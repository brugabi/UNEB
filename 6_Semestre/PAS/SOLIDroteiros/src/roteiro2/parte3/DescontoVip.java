package roteiro2.parte3;

public class DescontoVip implements RegraDesconto{

    @Override
    public double calcular(double total){
        return total * 0.8; //20%
    }
}


