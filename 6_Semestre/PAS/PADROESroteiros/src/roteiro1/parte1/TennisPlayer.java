package roteiro1.parte1;

public class TennisPlayer extends Player {
    public TennisPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Rebate a bola por cima do oponente");
    }
}
