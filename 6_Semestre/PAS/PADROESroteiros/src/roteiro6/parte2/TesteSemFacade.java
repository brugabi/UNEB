package roteiro6.parte2;

public class TesteSemFacade {

    public static void main(String[] args) {
        // Criando uma referencia para o banco de dados
        Database db = new Database();

        // Criando o cliente e adicionando ao banco de dados
        Cliente cli01 = new Cliente(1, "Jose");
        db.addCliente(cli01);

        // Criando o carrinho de compras e adicionando ao cliente criado
        CarrinhoCompra carrinho = new CarrinhoCompra();
        cli01.setCarrinho(carrinho);

        // Cliente 01 comprando os produtos 1 e 2 que estão armazenados no DB
        Produto p1 = db.selectProduto(1);
        Produto p2 = db.selectProduto(2);
        cli01.getCarrinho().addProduto(p1);
        cli01.getCarrinho().addProduto(p2);

        // Finalizando a compra
        double total = cli01.getCarrinho().getTotalCompra();
        db.processarPagamento(cli01, total);
    }
}
