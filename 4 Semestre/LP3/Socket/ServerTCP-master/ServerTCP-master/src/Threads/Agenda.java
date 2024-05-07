package Threads;

import java.util.Scanner;

public class Agenda {
    private String email;
    private String nome;
    private int ano;



    public Agenda(String email, String nome, int ano) {
        this.email = email;
        this.nome = nome;
        this.ano = ano;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public static Agenda criarAgenda(String nome, String email, String anoS){
        int ano = Integer.parseInt(anoS);
        Scanner in = new Scanner(System.in);

        return new Agenda(email,nome,ano);
    }
}


