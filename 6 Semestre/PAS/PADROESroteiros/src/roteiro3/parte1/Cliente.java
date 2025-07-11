package roteiro3.parte1;

public class Cliente {

    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public void receberNotificacao(String mensagem) {
        System.out.println("----------- NOTIFICACAO CLIENTE --------------------------------");
        System.out.println("Email enviado para " + nome + " (" + email + ")");
        System.out.println("Mensagem: " + mensagem);
        System.out.println("-------------------------------------------");
    }
}
