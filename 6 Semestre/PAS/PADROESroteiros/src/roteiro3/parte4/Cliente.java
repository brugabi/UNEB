package roteiro3.parte4;

public class Cliente implements Observer {

    private String nome;
    private String email;
    private String telefone; // 1. ATRIBUTO ADICIONADO
    private NotificacaoStrategy estrategiaNotificacao;


    public Cliente(String nome, String email, String telefone, NotificacaoStrategy notificacaoStrategy) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.estrategiaNotificacao = notificacaoStrategy;
    }

    // Setter para a estratégia (importante para o padrão Strategy)
    public void setEstrategiaNotificacao(NotificacaoStrategy estrategiaNotificacao) {
        this.estrategiaNotificacao = estrategiaNotificacao;
    }

    @Override
    public String getTipoObserver() {
        return "CLIENTE";
    }

    @Override
    public void update(String mensagem) {
        if (estrategiaNotificacao == null) {
            System.out.println("Estratégia de notificação não definida para o cliente " + nome);
            return;
        }

        String destinatario = (estrategiaNotificacao instanceof NotificacaoEmail) ? email : telefone;

        StringBuilder mensagemFormatada = new StringBuilder();
        mensagemFormatada.append("---------------NOTIFICACAO " + getTipoObserver() + "------------------\n");
        mensagemFormatada.append("Notificação enviada para " + nome + " (" + destinatario + ")\n");
        mensagemFormatada.append(mensagem+"\n");
        mensagemFormatada.append("-----------------------------------------------------------------------\n");

        this.estrategiaNotificacao.enviarMensagem(destinatario, mensagemFormatada.toString());
    }
}