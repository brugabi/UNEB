package roteiro1.parte2;

public abstract class Player {
    protected String nome;

    public Player (String nome) {
        this.nome = nome;
    }
    public void treinar(){
        System.out.println(this.nome + " Executando o treino !");
    }
    public void estiloCompetidor(){
        System.out.println(this.nome + " Muito competitivo !");
    }

    public void correr(){
        System.out.println(this.nome + " Corre muito durante o jogo !");
    }

    public abstract void definirTatica();
}
