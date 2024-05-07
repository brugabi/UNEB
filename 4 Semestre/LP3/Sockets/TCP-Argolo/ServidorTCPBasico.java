import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCPBasico {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Calculadora calculadora = new Calculadora();

        try {

            int porta = 12345;
            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("Servidor ouvindo na porta " + porta);
            
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                

                BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter saidaCliente = new PrintWriter(cliente.getOutputStream(), true);


                while (true) {
                    saidaCliente.println("\n\n ---- AGENDA ---- \n\n");
                    saidaCliente.println("Digite uma opção\n\n");
                    saidaCliente.println(" 1 - Cadastrar pessoa");
                    saidaCliente.println(" 2 - Buscar pessoa");
                    saidaCliente.println(" 3 - Listar pessoas");
                    saidaCliente.println(" 4 - Remover pessoa da agenda");
                    saidaCliente.println(" 5 - Atualizar informações");
                    saidaCliente.println(" 6 - Acessar calculadora");
                    saidaCliente.println(" 7 - Sair");
                    saidaCliente.println("-------------------------");

                    String resposta = entradaCliente.readLine();
                    saidaCliente.println("-------------------------");

                    if (resposta.equals("7")) {
                        break;
                    }

                    switch (resposta) {
                        case "1":
                            saidaCliente.println("Digite o nome: ");
                            String nome = entradaCliente.readLine();
                            saidaCliente.println("Digite o e-mail: ");
                            String email = entradaCliente.readLine();
                            saidaCliente.println("Digite o telefone: ");
                            String tel = entradaCliente.readLine();
                            Pessoa p = new Pessoa(nome, email, tel);
                            agenda.cadastrarPessoa(p, agenda);
                            saidaCliente.println("\nPessoa adicionada com sucesso!");
                            break;

                        case "2":
                        
                            Boolean vazia = agenda.verificarListaVazia(agenda, saidaCliente);

                            if (vazia) {
                                break;
                            }
                            
                            saidaCliente.println("Buscar por\n");
                            saidaCliente.println(" 1 - Nome");
                            saidaCliente.println(" 2 - E-mail");
                            saidaCliente.println(" 3 - Telefone\n");

                            saidaCliente.println("-------");
                            String opcao = entradaCliente.readLine();

                            switch (opcao) {
                                case "1":
                                    saidaCliente.println("Digite o nome da pessoa que deseja buscar: ");
                                    String nomeP = entradaCliente.readLine();
                                    saidaCliente.println("-----------");
                                    agenda.buscarPorNome(nomeP, agenda, saidaCliente);
                                    break;

                                case "2":
                                    saidaCliente.println("Digite o e-mail da pessoa que deseja buscar: ");
                                    String emailP = entradaCliente.readLine();
                                    saidaCliente.println("-----------");
                                    agenda.buscarPorEmail(emailP, agenda, saidaCliente);
                                    break;

                                case "3":
                                    saidaCliente.println("Digite o telefone da pessoa que deseja buscar: ");
                                    String telP = entradaCliente.readLine();
                                    saidaCliente.println("-----------");
                                    agenda.buscarPorTel(telP, agenda, saidaCliente);
                                    break;

                                default:
                                    saidaCliente.println("Opção inválida");    
                            }

                        case "3":
                            
                            Boolean vazia2 = agenda.verificarListaVazia(agenda, saidaCliente);
                            if (vazia2) {
                                break;  
                            }
                            agenda.listar(saidaCliente);
                            break;

                        case "4":
                            Boolean vazia3 = agenda.verificarListaVazia(agenda, saidaCliente);
                            if (vazia3) {
                                break;
                            }
                            saidaCliente.println("Qual pessoa deseja remover da agenda? ");
                            agenda.listar(saidaCliente);
                            saidaCliente.println("------------");
                            saidaCliente.println("Nome: ");
                            String nomeR = entradaCliente.readLine();
                            agenda.removerPessoa(nomeR, agenda, saidaCliente);
                            break;

                        case "5":
                            Boolean vazia4 = agenda.verificarListaVazia(agenda, saidaCliente);
                            if (vazia4) {
                                break;
                            }
                            saidaCliente.println("Qual pessoa deseja atualizar? ");
                            agenda.listar(saidaCliente);
                            saidaCliente.println("------------");
                            saidaCliente.println("Nome: ");
                            String nomeU = entradaCliente.readLine();
                            agenda.atualizarPessoa(nomeU, agenda, saidaCliente, entradaCliente);
                            break;

                        case "6":
                            saidaCliente.println("Digite um número:");
                            String num = entradaCliente.readLine();
                            saidaCliente.println("Digite outro número para a operação:");
                            String num2 = entradaCliente.readLine();

                            saidaCliente.println(" 1 - Soma");
                            saidaCliente.println(" 2 - Subtrair");
                            saidaCliente.println(" 3 - Multiplicar");
                            saidaCliente.println(" 4 - Dividir");
                            saidaCliente.println("-------");
                            String op = entradaCliente.readLine();

                            switch (op) {
                                case "1":
                                    calculadora.somar(num, num2, saidaCliente);
                                    break;
                                case "2":
                                    calculadora.subtrair(num, num2, saidaCliente);
                                    break;
                                case "3":
                                    calculadora.multiplicar(num, num2, saidaCliente);
                                    break;
                                case "4":
                                    calculadora.dividir(num, num2, saidaCliente);
                                    break;
                                default:
                                    saidaCliente.println("Opção inválida, tente novamente");

                            }    
                            saidaCliente.println("-------");
                            break;

                        case "7":
                            saidaCliente.println("Saindo...");
                            // try {
                            //     // Espera por 5 segundos
                            //     Thread.sleep(1500); // 1500 milissegundos = 1.5 segundos
                            // } catch (InterruptedException e) {
                            //     // Se a thread for interrompida enquanto dorme, trata a exceção
                            //     e.printStackTrace();
                            // }
                            
                            break;
                        default:
                            saidaCliente.println("Opção inválida, tente novamente");
                            break;
                    } 
                }
            }
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } 
    }
}