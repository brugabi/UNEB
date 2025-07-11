package roteiro1.parte2;

public class GolfPlayer extends Player {
    public GolfPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + "- Pontua com o nº de tacadas previstas");
    }

    @Override
    public void correr() {
        System.out.println(super.nome + " - Não Corre ! Ele pensa !");
    }
}
