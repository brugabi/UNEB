package Questao8;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Calculadora calculadora = new Calculadora();
        for (int i = 1; i <=4 ; i++) {
            CalcThread calcThread = new CalcThread(i+2,i,calculadora);
            Thread thread = new Thread(calcThread);
            thread.start();
            //thread.join();
        }
        Thread.sleep(100);
        System.out.println("O resultado final é " + calculadora.getResultado());
    }
}
