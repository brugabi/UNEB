public class Calculadora {
    private String expressao;


    private int soma(int num1, int num2){
        return num1 + num2;
    }

    private int subtracao(int num1,int num2){
        return num1-num2;

    }

    private int divisao(int num1,int num2){
        return num1/num2;

    }
    private int mult(int num1, int num2){
        return num1 * num2;
    }

    public int opcao(char op, int num1, int num2){
        return switch (op) {
            case '+' -> soma(num1, num2);
            case '-' -> subtracao(num1, num2);
            case '/' -> divisao(num1, num2);
            case '*' -> mult(num1, num2);
            default -> 0;
        };
    }
}
