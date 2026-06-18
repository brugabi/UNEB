package roteiro8.parte1;

public class TesteControlador {

    public static void main(String[] args) {
        testeControladorContabil();
    }

    public static void testeControladorContabil(){
        System.out.println("Criando o Controlador Contabil");
        ControladorContabil controladorcontabil = new ControladorContabil();

        System.out.println("Teste de Integração do Controlador Contabil - Sistema Contabil DELL");
        controladorcontabil.criarSistemaContabilAdapter("DELL");
        controladorcontabil.calcularImposto();

        System.out.println("Teste de Integração do Controlador Contabil - Sistema Contabil IBM");
        controladorcontabil.criarSistemaContabilAdapter("IBM");
        controladorcontabil.calcularImposto();
    }
}
