public class Pessoa {
    private String nome;
    private String email;
    private String tel;

    public Pessoa(String nome, String email, String tel) {
        this.nome = nome;
        this.email = email;
        this.tel = tel;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTel() { return tel; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    
    
}
