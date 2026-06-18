package roterio10.parte2;

public class Usuario {
    private String nome;
    private int idade;
    private String cpf;
    private IReceitaFederal pessoaFisica;

    public Usuario (String nome, int idade, String cpf){
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.pessoaFisica = new PessoaFisicaReceitaFederalProxy(cpf);
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean validarNome(){
        return this.getNome().equals(this.pessoaFisica.getNome());
    }

    public boolean verificaCPFAtivo(){
        return this.pessoaFisica.isCpfAtivo();
    }
}
