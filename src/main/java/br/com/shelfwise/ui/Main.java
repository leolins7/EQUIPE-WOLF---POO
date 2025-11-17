// MODIFICADO: src/main/java/br/com/shelfwise/ui/Main.java
package br.com.shelfwise.ui;

import java.util.List;
import java.util.Scanner;

import br.com.shelfwise.domain.Emprestimo;
import br.com.shelfwise.domain.Livro;
import br.com.shelfwise.domain.Membro;
import br.com.shelfwise.exception.ValidacaoException;
import br.com.shelfwise.repository.EmprestimoRepository;
import br.com.shelfwise.repository.LivroRepository;
import br.com.shelfwise.repository.MembroRepository;
import br.com.shelfwise.service.BibliotecaService;

public class Main {
    
    
    private static final LivroRepository livroRepository = new LivroRepository();
    private static final MembroRepository membroRepository = new MembroRepository();
    private static final EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
    private static final BibliotecaService bibliotecaService = new BibliotecaService(livroRepository, membroRepository, emprestimoRepository);
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("### ShelfWise - Sistema de Gerenciamento de Biblioteca ###");
        System.out.println("---------------------------------------------------------");
        
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consome a quebra de linha
                    processarOpcao(opcao);
                } else {
                    System.out.println("ERRO: Entrada inválida. Por favor, digite um número.");
                    scanner.nextLine(); // Limpa a entrada inválida
                }
            } catch (ValidacaoException e) {
                // Captura erros de regra de negócio vindos da camada de serviço
                System.out.println("\n!! ERRO DE NEGÓCIO: " + e.getMessage() + " !!");
            } catch (Exception e) {
                // Captura outros erros inesperados
                System.out.println("\n!! ERRO INESPERADO: " + e.getMessage() + " !!");
                e.printStackTrace();
            }
        }
        System.out.println("\nSistema encerrado. Até logo!");
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=============================================");
        System.out.println("            MENU PRINCIPAL");
        System.out.println("=============================================");
        System.out.println("1. Pesquisar Livros por Título (História 1)");
        System.out.println("2. Cadastrar Novo Membro (História 2)");
        System.out.println("3. Gerenciar Livros (Admin - História 6)");
        System.out.println("4. Gerenciar Empréstimos (Admin - História 7)");
        System.out.println("5. Ver Status do Acervo e Membros");
        System.out.println("6. Ver Meus Empréstimos (Membro - História 10)");
        System.out.println("7. Relatórios (Admin - Histórias 8 e 9)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                menuPesquisarLivros();
                break;
            case 2:
                menuCadastrarMembro();
                break;
            case 3:
                menuGerenciarLivros();
                break;
            case 4:
                menuGerenciarEmprestimos();
                break;
            case 5:
                menuStatusSistema();
                break;
            case 6: // Nova Opção
                menuMeusEmprestimos();
                break;
            case 7: // Nova Opção
                menuRelatorios();
                break;
            case 0:
                // Sair é tratado no loop principal
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
    
    // --- História 1: Pesquisa de Livros ---
    private static void menuPesquisarLivros() {
        System.out.println("\n--- Pesquisar Livros ---");
        System.out.print("Digite o termo para busca por título: ");
        String termo = scanner.nextLine();
        
        List<Livro> resultados = bibliotecaService.pesquisarLivros(termo);
        
        // Cenário 1.2: Pesquisa sem resultado
        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado para \"" + termo + "\".");
        } else {
            // Cenário 1.1: Pesquisa com sucesso
            System.out.println("Resultados encontrados (" + resultados.size() + "):");
            for (Livro livro : resultados) {
                System.out.println(livro);
            }
        }
    }
    
    // --- História 2: Cadastro de Novo Membro ---
    private static void menuCadastrarMembro() {
        System.out.println("\n--- Cadastro de Novo Membro ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Email institucional (@cesar.school): ");
        String email = scanner.nextLine();
        System.out.print("Crie uma senha: ");
        String senha = scanner.nextLine(); // A senha é capturada mas não usada para login ainda

        // A camada de UI não valida. Ela manda para o Serviço.
        // O try-catch no main() vai capturar o erro se o email for inválido.
        Membro novoMembro = bibliotecaService.cadastrarMembro(nome, email, senha);
        System.out.println("SUCESSO: Membro " + novoMembro.getNome() + " cadastrado com ID " + novoMembro.getId());
    }

    // --- História 6: Gerenciamento de Livros ---
    private static void menuGerenciarLivros() {
        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- Gerenciar Livros (Admin) ---");
            System.out.println("1. Adicionar Novo Livro");
            System.out.println("2. Remover Livro por ISBN");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                subOpcao = scanner.nextInt();
                scanner.nextLine();
                switch (subOpcao) {
                    case 1: // Adicionar Livro
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String isbnAdd = scanner.nextLine();
                        bibliotecaService.adicionarLivro(titulo, autor, isbnAdd);
                        System.out.println("SUCESSO: Livro '" + titulo + "' adicionado ao acervo.");
                        break;
                    case 2: // Remover Livro
                        System.out.print("ISBN do livro a ser removido: ");
                        String isbnRemover = scanner.nextLine();
                        bibliotecaService.removerLivro(isbnRemover);
                        System.out.println("SUCESSO: Livro com ISBN " + isbnRemover + " removido do acervo.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("ERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    // --- História 7: Gerenciamento de Empréstimos ---
    private static void menuGerenciarEmprestimos() {
        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- Gerenciar Empréstimos/Devoluções (Admin) ---");
            System.out.println("1. Registrar Novo Empréstimo");
            System.out.println("2. Registrar Devolução");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                subOpcao = scanner.nextInt();
                scanner.nextLine();
                switch (subOpcao) {
                    case 1: // Registrar Empréstimo
                        System.out.print("ID do Membro: ");
                        if (scanner.hasNextInt()) {
                            int idMembro = scanner.nextInt();
                            scanner.nextLine(); 
                            System.out.print("ISBN do Livro: ");
                            String isbnEmprestimo = scanner.nextLine();
                            bibliotecaService.registrarEmprestimo(isbnEmprestimo, idMembro);
                            System.out.println("SUCESSO: Empréstimo registrado.");
                        } else {
                            System.out.println("ERRO: ID do Membro deve ser um número.");
                            scanner.nextLine();
                        }
                        break;
                    case 2: // Registrar Devolução
                        System.out.print("ISBN do Livro a ser devolvido: ");
                        String isbnDevolucao = scanner.nextLine();
                        bibliotecaService.registrarDevolucao(isbnDevolucao);
                        System.out.println("SUCESSO: Devolução registrada. Livro agora está disponível.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("ERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }
    
    // --- Menu de Status ---
    private static void menuStatusSistema() {
        System.out.println("\n--- STATUS DO ACERVO ---");
        List<Livro> acervo = bibliotecaService.getStatusAcervo();
        if (acervo.isEmpty()) {
             System.out.println("O acervo está vazio.");
        } else {
            acervo.forEach(System.out::println);
        }
        
        System.out.println("\n--- MEMBROS CADASTRADOS ---");
        List<Membro> membros = bibliotecaService.getStatusMembros();
        if (membros.isEmpty()) {
            System.out.println("Nenhum membro cadastrado.");
        } else {
            membros.forEach(System.out::println);
        }
        System.out.println("---------------------------------");
    }

    // --- (NOVO) História 10: Meus Empréstimos ---
    private static void menuMeusEmprestimos() {
        System.out.println("\n--- Meus Empréstimos (Área do Membro) ---");
        System.out.print("Por favor, informe seu ID de Membro: ");
        
        if (scanner.hasNextInt()) {
            int idMembro = scanner.nextInt();
            scanner.nextLine();
            
            List<Emprestimo> meusEmprestimos = bibliotecaService.getEmprestimosPorMembro(idMembro);
            
            if (meusEmprestimos.isEmpty()) {
                System.out.println("Você não possui empréstimos ativos no momento.");
            } else {
                System.out.println("Você possui " + meusEmprestimos.size() + " empréstimo(s) ativo(s):");
                meusEmprestimos.forEach(System.out::println);
            }
        } else {
            System.out.println("ERRO: ID do Membro deve ser um número.");
            scanner.nextLine();
        }
    }
    
    // --- (NOVO) Histórias 8 e 9: Relatórios Admin ---
    private static void menuRelatorios() {
        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- Relatórios (Admin) ---");
            System.out.println("1. Listar TODOS os Empréstimos Ativos (História 8)");
            System.out.println("2. Listar Empréstimos ATRASADOS (História 9)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                subOpcao = scanner.nextInt();
                scanner.nextLine();
                switch (subOpcao) {
                    case 1: // H8: Listar Ativos
                        List<Emprestimo> ativos = bibliotecaService.getEmprestimosAtivos();
                        if (ativos.isEmpty()) {
                            System.out.println("Não há nenhum empréstimo ativo no sistema.");
                        } else {
                            System.out.println("Total de Empréstimos Ativos: " + ativos.size());
                            ativos.forEach(System.out::println);
                        }
                        break;
                    case 2: // H9: Listar Atrasados
                        List<Emprestimo> atrasados = bibliotecaService.getEmprestimosAtrasados();
                        if (atrasados.isEmpty()) {
                            System.out.println("Não há empréstimos com devolução atrasada.");
                        } else {
                            System.out.println("Total de Empréstimos Atrasados: " + atrasados.size());
                            atrasados.forEach(System.out::println);
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                System.out.println("ERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }
}