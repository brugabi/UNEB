package Questao8;

public class Calculadora {
    private double resultado = 0;

    public void finalizarResultado(double num, int op){
        switch (op) {
            case 1 -> this.resultado += num;
            case 2 -> this.resultado -= num;
            case 3 -> this.resultado /= num;
            case 4 -> this.resultado *= num;
        }
    }

    public double getResultado() {
        return resultado;
    }
}
