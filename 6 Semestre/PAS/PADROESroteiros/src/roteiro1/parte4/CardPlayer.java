package roteiro1.parte4;

public class CardPlayer extends Player {
    public CardPlayer(String name, RunBehavior habilidadeCorrer) {
        super(name, habilidadeCorrer);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " É um jogador muito calmo");
    }

}
