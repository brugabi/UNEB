package roterio10.parte2;

import java.util.concurrent.TimeUnit;

public class PessoaFisicaReceitaFederal implements IReceitaFederal {
    private String nome;
    private int idade;
    private String cpf;
    private boolean cpfAtivo;
    public PessoaFisicaReceitaFederal(String cpf){
        try {
            this.cpf = cpf;
            this.nome = "Fulano";
            this.idade = 30;
            this.cpfAtivo = true;
            TimeUnit.SECONDS.sleep(5);
            System.out.println("PessoaFisicaReceitaFederal criada com Sucesso ");
        } catch (Exception e) {
            throw new UnsupportedOperationException("Falha - " + e.getMessage());
        }
    }

    public String getNome() {
        try {
            TimeUnit.SECONDS.sleep(2);
            return nome;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Falha - " + e.getMessage());
        }
    }

    public int getIdade() {
        try {
            TimeUnit.SECONDS.sleep(2);
            return idade;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Falha - " + e.getMessage());
        }
    }

    public String getCpf() {
        try {
            TimeUnit.SECONDS.sleep(2);
            return cpf;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Falha - " + e.getMessage());
        }
    }

    public boolean isCpfAtivo() {
        try {
            TimeUnit.SECONDS.sleep(3);
            return cpfAtivo;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Falha - " + e.getMessage());
        }
    }
}
