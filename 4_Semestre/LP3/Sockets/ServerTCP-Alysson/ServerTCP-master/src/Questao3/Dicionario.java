package Questao3;

import java.util.HashMap;
import java.util.Map;

public class Dicionario {
    Map<String,String>dicionario;

    public Map<String, String> getDicionario() {
        return dicionario;
    }

    public Dicionario() {
        // Inicializa o dicionário vazio
        this.dicionario = new HashMap<>();
    }

    // Adiciona uma palavra e seu significado ao dicionário
    public void adicionarPalavra(String palavra, String significado) {
        this.dicionario.put(palavra, significado);
    }

    // Obtém o significado de uma palavra no dicionário
    public String obterSignificado(String palavra) {
        return this.dicionario.get(palavra);
    }

}
