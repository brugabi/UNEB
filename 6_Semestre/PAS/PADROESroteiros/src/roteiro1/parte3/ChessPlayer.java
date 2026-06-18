package roteiro1.parte3;

public class ChessPlayer extends Player {
    public ChessPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Domina o centro do tabuleiro");
    }

}
