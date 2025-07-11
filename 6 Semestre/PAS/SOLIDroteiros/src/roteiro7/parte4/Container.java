package roteiro7.parte4;

public abstract class Container {

    protected Borda borda;

    public Container(){

    }
    public Container (Borda borda){
        this.borda = borda;
    }
    public abstract void addComponent (Component c);

    public abstract void removeComponent (Component c);

    public abstract void doLayout();

    public void dispose(){
        System.out.println("Fechando o Container ");
    }
}
