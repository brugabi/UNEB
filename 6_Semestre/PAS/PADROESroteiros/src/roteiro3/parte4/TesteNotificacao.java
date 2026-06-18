package roteiro3.parte4;

public class TesteNotificacao {

    public static void main(String[] args) {

        // --- 1. CONFIGURAÇÃO INICIAL ---
        Newsletter newsletter = new Newsletter();

        // Estratégias disponíveis
        NotificacaoStrategy email = new NotificacaoEmail();
        NotificacaoStrategy sms = new NotificacaoSMS();
        NotificacaoStrategy whatsapp = new NotificacaoWhatsApp();

        // Criando os observers com suas estratégias iniciais
        Cliente cli01 = new Cliente("Cliente Jose", "jose@empresa.com", "71999990001", email);
        Cliente cli02 = new Cliente("Cliente Maria", "maria@empresa.com", "71999990002", sms);
        Funcionario func01 = new Funcionario("Funcionario Pedro", "pedro@empresa.com", "Diretor", "71999990003", whatsapp);
        Fornecedor fornecedor01 = new Fornecedor("Fornecedor Peças Já", "contato@pecasja.com", "11.222.333/0001-44", "71999990004", email);

        // Adicionando observers na newsletter
        newsletter.adicionarObserver(cli01);
        newsletter.adicionarObserver(cli02);
        newsletter.adicionarObserver(func01);
        newsletter.adicionarObserver(fornecedor01);

        // --- 2. PRIMEIRO ENVIO DE MENSAGEM ---
        System.out.println("================== PRIMEIRO ENVIO DE MENSAGEM ==================");
        System.out.println("Jose:EMAIL | Maria:SMS | Pedro:WHATSAPP | Fornecedor:EMAIL");
        System.out.println("==============================================================\n");
        newsletter.enviarMensagem("Ofertas de São João! Não perca os descontos juninos.");

        // --- 3. ALTERANDO A ESTRATÉGIA DE NOTIFICAÇÃO EM TEMPO DE EXECUÇÃO ---
        System.out.println("\n\n================== ALTERANDO ESTRATÉGIAS ==================");
        System.out.println("O Cliente Jose prefere receber por WhatsApp agora.");
        System.out.println("O Funcionario Pedro está de férias e prefere receber por Email.");
        System.out.println("==============================================================\n");

        // Para chamar o método específico, fazemos o cast do objeto
        cli01.setEstrategiaNotificacao(whatsapp); // Jose mudou de Email para WhatsApp
        func01.setEstrategiaNotificacao(email);    // Pedro mudou de WhatsApp para Email
        // Maria e o Fornecedor continuam com suas estratégias originais (SMS e Email)

        // --- 4. SEGUNDO ENVIO DE MENSAGEM ---
        System.out.println("\n\n================== SEGUNDO ENVIO DE MENSAGEM ===================");
        System.out.println("Jose:WHATSAPP | Maria:SMS | Pedro:EMAIL | Fornecedor:EMAIL");
        System.out.println("==============================================================\n");
        newsletter.enviarMensagem("ÚLTIMO DIA das ofertas de São João!");
    }
}