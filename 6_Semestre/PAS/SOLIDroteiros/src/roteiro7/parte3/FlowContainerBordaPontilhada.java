package roteiro7.parte3;

import java.util.ArrayList;

public class FlowContainerBordaPontilhada extends FlowContainer {

    public FlowContainerBordaPontilhada() {
        super();
    }

    @Override
    public void doLayout() {
        System.out.println("O Container utilizado é o FlowContainer");
        System.out.println("Este container contêm bordas pontilhadas");
        System.out.println("Estes são os elementos presentes no container");
        System.out.println(super.elements);
        System.out.println("Usando o método dispose como herança para fechar o container");
        this.dispose();
        System.out.println("--------------------------------");

    }
}
