package roteiro7.parte3;

public class BancoBrasil extends Banco{

    @Override
    public Boleto criarBoleto(int vencimento, double valor) {
        switch (vencimento) {
            case 10: return new BoletoCaixa10Dias(valor);
            case 30: return new BoletoCaixa30Dias(valor);
            case 60: return new BoletoCaixa60Dias(valor);
            default:
                throw new UnsupportedOperationException("ERRO: Vencimento indisponível");
        }
    }
}
