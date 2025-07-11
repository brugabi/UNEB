package roteiro1.parte4;

public class ChessPlayer extends Player {
    public ChessPlayer(String name, RunBehavior habilidadecorrer) {
        super(name, habilidadecorrer);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Domina o centro do tabuleiro");
    }
}
