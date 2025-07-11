package roteiro6.parte4;

public class Facade {

    // 2. Atributo estático que guarda a instância única (criação Eager)
    private static Facade instance = new Facade();

    private Database db;
    private Cliente cliente;
    private Produto produto;

    // 1. Construtor privado para impedir o uso do 'new' fora da classe
    private Facade() {
        this.db = new Database();
    }

    // 3. Método público estático para fornecer acesso à instância única
    public static Facade getInstance() {
        return instance;
    }

    // Os métodos de negócio permanecem os mesmos
    public void registrarCliente(String nome, int id){
        this.cliente = new Cliente(id, nome);
        this.db.addCliente(this.cliente);
    }

    public void comprar(int productID, int clientID){
        this.cliente = db.selectCliente(clientID);
        this.produto = db.selectProduto(productID);
        this.cliente.getCarrinho().addProduto(this.produto);
    }

    public void finalizarCompra(int clientID){
        this.cliente = db.selectCliente(clientID);
        double total = this.cliente.calcularTotal();
        db.processarPagamento(cliente, total);
    }
}