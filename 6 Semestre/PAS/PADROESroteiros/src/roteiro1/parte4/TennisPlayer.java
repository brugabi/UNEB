package roteiro1.parte4;

public class TennisPlayer extends Player {
    public TennisPlayer(String name, RunBehavior habilidadeCorrer) {
        super(name, habilidadeCorrer);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Rebate a bola por cima do oponente");
    }
}
