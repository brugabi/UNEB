package roteiro5.parte1;

public class Teste {

    public static void main(String[] args) {

        ControladorAereo c1 = new ControladorAereo();
        ControladorAereo c2 = new ControladorAereo();

        c1.solicitarDecolagem();
        c2.solicitarDecolagem();

        System.out.println(" ");

        c1.solicitarAterrissagem();
        c2.solicitarAterrissagem();
    }
}
