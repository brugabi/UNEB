package roteiro1.parte3;

public class TennisPlayer extends Player implements Runnable {
    public TennisPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Rebate a bola por cima do oponente");
    }

    @Override
    public void correr() {
        System.out.println(this.nome + " Corre muito durante o jogo !");
    }
}
