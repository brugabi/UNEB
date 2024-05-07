package Questao8;

public class CalcThread implements Runnable {
    private double threadNum;
    private int threadOperationNum;
    private Calculadora calculadora;

    public  CalcThread(double threadNum, int threadOperationNum,  Calculadora calculadora){
        this.threadNum = threadNum;
        this.threadOperationNum = threadOperationNum;
        this.calculadora = calculadora;
    }

    @Override
    public void run() {
        //System.out.println("Thread " + this.threadOperationNum + "Fez o cálculo com o numero " + threadNum);
        this.calculadora.finalizarResultado(this.threadNum, threadOperationNum);
    }
}
