package roteiro7.parte1;

public abstract class Container {
    public abstract void addComponent (Component c);

    public abstract void removeComponent (Component c);

    public abstract void doLayout();

    public void dispose(){
        System.out.println("Fechando o Container ");
    }
}
