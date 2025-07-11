package roteiro3.parte3;

public class Fornecedor implements Observer {

    private String nome;
    private String email;
    private String cnpj;

    public Fornecedor(String nome, String email, String cnpj) {
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
    }

    @Override
    public void update(String mensagem) {
        System.out.println("----------- NOTIFICACAO FORNECEDOR --------------------------------");
        System.out.println("Email enviado para " + nome + " (" + email + ")");
        System.out.println("Mensagem: " + mensagem);
        System.out.println("-------------------------------------------");
    }
}
