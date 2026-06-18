package roteiro1.parte3;

public class CardPlayer extends Player {
    public CardPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " É um jogador muito calmo");
    }

}
