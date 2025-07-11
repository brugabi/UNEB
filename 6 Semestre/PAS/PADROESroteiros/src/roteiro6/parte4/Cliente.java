package roteiro6.parte4;

public class Cliente {
    private int id;
    private String nome;
    private CarrinhoCompra carrinho;

    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.carrinho = new CarrinhoCompra();
    }

    @Override
    public String toString() {
        return "Cliente : " + this.id + "\n"+
                "Nome : "+ this.nome + "\n";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CarrinhoCompra getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(CarrinhoCompra carrinho) {
        this.carrinho = carrinho;
    }

    public double calcularTotal(){
        return this.carrinho.getTotalCompra();
    }
}
