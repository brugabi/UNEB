package roteiro7.parte4;

import java.util.ArrayList;
import java.util.List;

public class FlowContainer extends Container {
    protected List<Component> elements;

    public FlowContainer() {
        this.elements = new ArrayList<Component>();
    }
    public FlowContainer(Borda borda) {
        super(borda);
        this.elements = new ArrayList<Component>();
    }
    @Override
    public void addComponent(Component c) {
        this.elements.add(c);
    }
    @Override
    public void removeComponent(Component c) {
        this.elements.remove(c);
    }
    @Override
    public void doLayout() {
        System.out.println("O Container utilizado é o FlowContainer");
        if(this.borda != null) {
            System.out.println("Tipo de Borda: " + this.borda.getClass().getSimpleName());
            this.borda.gerarBorda();
        }
        System.out.println("Estes são os elementos presentes no container");
        System.out.println(elements);
        System.out.println("Usando o método dispose como herança para fechar o container");
        this.dispose();
        System.out.println("--------------------------------");
    }
}
