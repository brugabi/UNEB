package roteiro2.parte1;

import java.util.Random;

public class Gateway {
    public boolean cobrar(double valor){
        System.out.println("Valor cobrado : "+ valor);

        Random random = new Random(); // Cria uma instância de Random
        boolean autorizado = random.nextBoolean(); // Gera um valor boolean aleatório como resposta do Gateway
        System.out.println("Cobrança Autorizada : "+ autorizado);

        return autorizado;
    }
}
