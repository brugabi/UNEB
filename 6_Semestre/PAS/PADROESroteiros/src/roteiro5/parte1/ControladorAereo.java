package roteiro5.parte1;

public class ControladorAereo {

    private boolean permitidoAterrissar;
    private boolean permitidoDecolar;

    public ControladorAereo() {
        this.permitidoAterrissar = false;
        this.permitidoDecolar = true;
    }

    public void solicitarAterrissagem(){
        if (this.permitidoAterrissar){
            System.out.println("Permissão para aterrissagem concedida ");
            this.permitidoAterrissar = false;
            this.permitidoDecolar = true;
        }
        else{
            System.out.println("Permissão para aterrissagem negada ");
        }
    }

    public void solicitarDecolagem(){
        if (this.permitidoDecolar){
            System.out.println("Permissão para decolar concedida ");
            this.permitidoAterrissar = true;
            this.permitidoDecolar = false;
        }
        else{
            System.out.println("Permissão para decolagem negada ");
        }
    }
}
