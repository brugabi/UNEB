package roteiro8.parte2;

public class TesteControlador {

    public static void main(String[] args) {
        testeControladorEstoque();
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
    public static void testeControladorEstoque(){
        System.out.println("Criando o Controlador de Estoque");
        ControladorEstoque controladorestoque = new ControladorEstoque();

        System.out.println("Teste de Integração do Controlador de Estoque - Sistema de Estoque DELL");
        controladorestoque.criarSistemaEstoqueAdapter("DELL");
        controladorestoque.aumentarQuantidadeItem();

        System.out.println("Teste de Integração do Controlador de Estoque - Sistema de Estoque IBM");
        controladorestoque.criarSistemaEstoqueAdapter("IBM");
        controladorestoque.aumentarQuantidadeItem();
    }
}
