package roteiro3.parte3;

public class Cliente implements Observer {

    private String nome;
    private String email;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    @Override
    public void update(String mensagem) {
        System.out.println("----------- NOTIFICACAO CLIENTE --------------------------------");
        System.out.println("Email enviado para " + nome + " (" + email + ")");
        System.out.println("Mensagem: " + mensagem);
        System.out.println("-------------------------------------------");
    }
}
