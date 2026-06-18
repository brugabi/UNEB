package roteiro1.parte2;

public class CardPlayer extends Player {
    public CardPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " É um jogador muito calmo");
    }

    @Override
    public void correr() {
        System.out.println(super.nome + " - Não Corre ! Ele pensa !");
    }
}
