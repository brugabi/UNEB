package roteiro5.parte1;

public class TesteNotificacao {
    public static void main(String[] args) {
        NotificacaoService notificacao = new NotificacaoService();

        notificacao.enviarNotificacaoEmail("Bem-vindo ao sistema!");
        notificacao.enviarNotificacaoSMS("Seu código de verificação é 1234.");
        notificacao.enviarNotificacaoPush("Você tem uma nova mensagem.");
    }
}
