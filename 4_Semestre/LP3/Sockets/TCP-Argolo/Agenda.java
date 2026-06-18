import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class Agenda {
    private List<Pessoa> pessoas;

    public Agenda() {
        pessoas = new ArrayList<>();
    }

    public Boolean verificarListaVazia(Agenda agenda, PrintWriter saidaCliente) {
        Boolean vazia = false;
        if (pessoas.isEmpty()) {
            saidaCliente.println("Agenda vazia");
            vazia = true;
        }
        return vazia;
    }

    public void cadastrarPessoa(Pessoa pessoa, Agenda agenda) {
        agenda.pessoas.add(pessoa);
    }

    public void buscarPorNome(String nome, Agenda agenda, PrintWriter saidaCliente) {
        for (Pessoa p : agenda.pessoas) {
            if (p.getNome().equals(nome)) {
                saidaCliente.println("Nome: " + p.getNome());
                saidaCliente.println("Email: " + p.getEmail());
                saidaCliente.println("Telefone: " + p.getTel());
                return;
            }
        }
        saidaCliente.println("Pessoa nao encontrada");
    }

    public void buscarPorEmail(String email, Agenda agenda, PrintWriter saidaCliente) {
        for (Pessoa p : agenda.pessoas) {
            if (p.getEmail().equals(email)) {
                saidaCliente.println("Nome: " + p.getNome());
                saidaCliente.println("Email: " + p.getEmail());
                saidaCliente.println("Telefone: " + p.getTel());
                return;
            }
        }
        saidaCliente.println("Pessoa nao encontrada");
    }

    public void buscarPorTel(String tel, Agenda agenda, PrintWriter saidaCliente) {
        for (Pessoa p : agenda.pessoas) {
            if (p.getTel().equals(tel)) {
                saidaCliente.println("Nome: " + p.getNome());
                saidaCliente.println("Email: " + p.getEmail());
                saidaCliente.println("Telefone: " + p.getTel());
                return;
            }
        }
        saidaCliente.println("Pessoa nao encontrada");
    }

    public void listar(PrintWriter saidaCliente) {
        if (pessoas.isEmpty()) {
            saidaCliente.println("-------------------------");
            saidaCliente.println("\nLista vazia");
            return;
        }
        int c = 1;
        for (Pessoa p : pessoas) {
            saidaCliente.println(" " + c + " - " + p.getNome());
        }
    }

    public void removerPessoa(String nome, Agenda agenda, PrintWriter saidaCliente) {
        for (Pessoa p : agenda.pessoas) {
            if (p.getNome().equals(nome)) {
                agenda.pessoas.remove(p);
                saidaCliente.println("Pessoa removida com sucesso");
                return;
            }
        }
        saidaCliente.println("Pessoa nao encontrada");
    }

    public void atualizarPessoa(String nome, Agenda agenda, PrintWriter saidaCliente, BufferedReader entradaCliente) {
        try {
            for (Pessoa p : agenda.pessoas) {
                if (p.getNome().equals(nome)) {
                    saidaCliente.println("Qual campo deseja atualizar?");
                    saidaCliente.println(" 1 - Nome");
                    saidaCliente.println(" 2 - Email");
                    saidaCliente.println(" 3 - Telefone\n");
                    saidaCliente.println("-------");
                    String opcao = entradaCliente.readLine();
                    switch (opcao) {
                        case "1":
                            saidaCliente.println("Digite o novo nome: ");
                            String novoNome = entradaCliente.readLine();
                            p.setNome(novoNome);
                            saidaCliente.println("Nome atualizado");
                            break;

                        case "2":
                            saidaCliente.println("Digite o novo email: ");
                            String novoEmail = entradaCliente.readLine();
                            p.setEmail(novoEmail);
                            saidaCliente.println("Email atualizado");
                            break;

                        case "3":
                            saidaCliente.println("Digite o novo telefone: ");
                            String novoTel = entradaCliente.readLine();
                            p.setTel(novoTel);
                            saidaCliente.println("Telefone atualizado");
                            break;

                        default:
                            saidaCliente.println("Opção invalida");

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // public static Pessoa buscarPessoa(String info) {

    //     if (info.equals(info))

    //     return
    // }
}
