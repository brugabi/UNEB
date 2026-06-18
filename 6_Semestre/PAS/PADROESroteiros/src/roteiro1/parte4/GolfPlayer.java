package roteiro1.parte4;

public class GolfPlayer extends Player {
    public GolfPlayer(String name, RunBehavior habilidadeCorrer) {
        super(name, habilidadeCorrer);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + "- Pontua com o nº de tacadas previstas");
    }

}
