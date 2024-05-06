public class Candidato {
    private String nome;
    private int votos = 0;

    public Candidato(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos() {
        this.votos += 1;
    }

    @Override
    public String toString() {
        return nome + " " + votos;

    }
}
