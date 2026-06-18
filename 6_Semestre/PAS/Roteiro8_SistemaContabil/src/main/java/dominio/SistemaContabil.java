/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

public class SistemaContabil {
    private String nome;

    public SistemaContabil(String nome) {
        this.nome = nome;
    }

    public void registrarVenda(){
        System.out.println("Venda Registrada no "+this.nome + " - Sistema Contabil \n");
    }

    public void calcularImposto(){
        System.out.println("Imposto calculado no "+this.nome + " - Sistema Contabil \n");
        
    }
    
}
