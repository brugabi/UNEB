import java.io.PrintWriter;

public class Calculadora {

    private int n1;
    private int n2;

    public Calculadora() {
        
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public void setN2(int n2) { 
        this.n2 = n2;
    }

    public void somar(String num1, String num2, PrintWriter saida) {
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        saida.println("A soma de " + num1 + " e " + num2 + " é " + (n1 + n2)); ;
    }
    
    public void subtrair(String num1, String num2, PrintWriter saida) {
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        saida.println("A diferença de " + num1 + " e " + num2 + " é " + (n1 - n2)); 
    }
    
    public void multiplicar(String num1, String num2, PrintWriter saida) {
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        saida.println("A multiplicação de " + num1 + " e " + num2 + " é " + (n1 * n2));
    }
    
    public void dividir(String num1, String num2, PrintWriter saida) {
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        saida.println("A divisão de " + num1 + " por " + num2 + " é " + (n1 / n2));
    }
}
