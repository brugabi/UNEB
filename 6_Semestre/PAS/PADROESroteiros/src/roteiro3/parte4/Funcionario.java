package roteiro3.parte4;

public class Funcionario implements Observer {

    private String nome;
    private String email;
    private String cargo;
    private String telefone; // 1. ATRIBUTO ADICIONADO
    private NotificacaoStrategy estrategiaNotificacao;


    public Funcionario(String nome, String email, String cargo, String telefone, NotificacaoStrategy whatsapp) {
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
        this.telefone = telefone;
        this.estrategiaNotificacao = whatsapp;
    }


    public void setEstrategiaNotificacao(NotificacaoStrategy estrategiaNotificacao) {
        this.estrategiaNotificacao = estrategiaNotificacao;
    }

    @Override
    public String getTipoObserver() {
        return "FUNCIONARIO";
    }


    @Override
    public void update(String mensagem) {
        if (estrategiaNotificacao == null) {
            System.out.println("Estratégia de notificação não definida para o funcionário " + nome);
            return;
        }

        String destinatario = (estrategiaNotificacao instanceof NotificacaoEmail) ? email : telefone;


        StringBuilder mensagemFormatada = new StringBuilder();
        mensagemFormatada.append("---------------NOTIFICACAO " + getTipoObserver() + "------------------\n");
        mensagemFormatada.append("Comunicado para " + nome + " (Cargo: " + cargo + ")\n");
        mensagemFormatada.append("Destinatário: " + destinatario + "\n");
        mensagemFormatada.append(mensagem + "\n");
        mensagemFormatada.append("-----------------------------------------------------------------------\n");

        // Usa a estratégia para efetuar o envio
        this.estrategiaNotificacao.enviarMensagem(destinatario, mensagemFormatada.toString());
    }
}