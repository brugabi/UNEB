package roteiro1.parte2;

public class SoccerPlayer extends Player {
    public SoccerPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Trabalha em equipe !");
    }
}
