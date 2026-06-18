package roteiro1.parte3;

public class SoccerPlayer extends Player implements Runnable {
    public SoccerPlayer(String name) {
        super(name);
    }

    @Override
    public void definirTatica() {
        System.out.println(super.nome + " Trabalha em equipe !");
    }

    @Override
    public void correr() {
        System.out.println(this.nome + " Corre muito durante o jogo !");
    }
}
