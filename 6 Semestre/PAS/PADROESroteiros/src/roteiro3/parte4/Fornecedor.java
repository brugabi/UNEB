package roteiro3.parte4;

public class Fornecedor implements Observer {

    private String nome;
    private String email;
    private String cnpj;
    private String telefone; // 1. ATRIBUTO ADICIONADO
    private NotificacaoStrategy estrategiaNotificacao;


    public Fornecedor(String nome, String email, String cnpj, String telefone, NotificacaoStrategy notificacaoStrategy) {
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.estrategiaNotificacao = notificacaoStrategy;
    }

    // 3. SETTER ADICIONADO para injetar a estratégia de notificação
    public void setEstrategiaNotificacao(NotificacaoStrategy estrategiaNotificacao) {
        this.estrategiaNotificacao = estrategiaNotificacao;
    }

    @Override
    public String getTipoObserver() {
        return "FORNECEDOR";
    }

    @Override
    public void update(String mensagem) {
        if (estrategiaNotificacao == null) {
            System.out.println("Estratégia de notificação não definida para o fornecedor " + nome);
            return;
        }

        String destinatario = (estrategiaNotificacao instanceof NotificacaoEmail) ? email : telefone;


        StringBuilder mensagemFormatada = new StringBuilder();
        mensagemFormatada.append("---------------NOTIFICACAO " + getTipoObserver() + "------------------\n");
        mensagemFormatada.append("Notificação enviada para " + nome + " (CNPJ: " + cnpj + ")\n");
        mensagemFormatada.append("Destinatário: " + destinatario + "\n");
        mensagemFormatada.append(mensagem + "\n");
        mensagemFormatada.append("-----------------------------------------------------------------------\n");


        this.estrategiaNotificacao.enviarMensagem(destinatario, mensagemFormatada.toString());
    }
}