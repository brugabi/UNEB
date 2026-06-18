package roteiro3.parte1;

public class TesteNotificacao {

    public static void main(String[] args) {

        Newsletter newsletter = new Newsletter();

        Cliente cli01 = new Cliente("Cliente Jose", "jose@empresa.com");
        Cliente cli02 = new Cliente("Cliente Maria", "maria@empresa.com");

        newsletter.adicionarCliente(cli01);
        newsletter.adicionarCliente(cli02);

        newsletter.enviarMensagem("Oferta Especial !");
    }
}
