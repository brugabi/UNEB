package roteiro3.parte1;

import java.util.ArrayList;

public class Newsletter {

    private ArrayList<Cliente> clientes;

    public Newsletter() {
        this.clientes = new ArrayList<>();
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public void enviarMensagem(String mensagem) {
        for (Cliente cliente : clientes) {
            cliente.receberNotificacao(mensagem);
        }
    }
}
