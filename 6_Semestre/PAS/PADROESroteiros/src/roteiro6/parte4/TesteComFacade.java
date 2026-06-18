package roteiro6.parte4;

public class TesteComFacade {
    public static void main(String[] args) {
        // Acesso à fachada através do método estático getInstance()
        Facade facade = Facade.getInstance();

        System.out.println("--- Realizando compra com a primeira variável da facade ---");
        facade.registrarCliente("Jose", 222);
        facade.comprar(1, 222);
        facade.finalizarCompra(222);

        System.out.println("\n--- Tentando usar a facade a partir de outra variável ---");
        // Provando que é a mesma instância com o mesmo estado
        Facade outraFacade = Facade.getInstance();
        outraFacade.comprar(2, 222); // Continua a compra do mesmo cliente
        outraFacade.finalizarCompra(222);
    }
}