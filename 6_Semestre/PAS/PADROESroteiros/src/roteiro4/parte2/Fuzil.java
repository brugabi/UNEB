package roteiro4.parte2;

public class Fuzil implements Arma {

    @Override
    public void carregar() {
        System.out.println("Carregando o Fuzil");
    }

    @Override
    public void atirar() {
        System.out.println("Esse tiro faz um estrago !");
    }

    @Override
    public void mirar() {
        System.out.println("Alvo certeiro");
    }
}
