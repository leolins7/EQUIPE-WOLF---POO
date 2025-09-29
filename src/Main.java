import java.util.Scanner;

public class Main {
    private static SistemaBiblioteca sistema = new SistemaBiblioteca();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("### Sistema de Gerenciamento de Biblioteca - Stage 02 ###");
        
        // Dados iniciais para teste (para não ter que cadastrar Membro toda vez)
        sistema.adicionarMembro("Ana Silva", "ana.silva@biblioteca.com"); // ID 1
        sistema.adicionarMembro("Bruno Souza", "bruno.souza@biblioteca.com"); // ID 2
        
        // Menu principal
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a quebra de linha
                processarOpcao(opcao);
            } else {
                System.out.println("ERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }
        System.out.println("\nSistema encerrado. Até logo!");
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=============================================");
        System.out.println("            MENU PRINCIPAL");
        System.out.println("=============================================");
        System.out.println("1. Gerenciar Livros (História 6)");
        System.out.println("2. Gerenciar Empréstimos e Devoluções (História 7)");
        System.out.println("3. Ver Status do Acervo e Membros");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                menuGerenciarLivros();
                break;
            case 2:
                menuGerenciarEmprestimos();
                break;
            case 3:
                sistema.statusAcervo();
                // Exibe membros também
                System.out.println("\n--- MEMBROS CADASTRADOS ---");
                // Note: Esta função precisaria ser adicionada em SistemaBiblioteca
                // para listar todos os membros para simplificar o teste.
                // Por enquanto, apenas avisa que membros foram cadastrados na inicialização.
                System.out.println("Membros pré-cadastrados: ID 1 (Ana), ID 2 (Bruno).");
                break;
            case 0:
                // Sair é tratado no loop principal
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    // --- Menu História 6: Gerenciamento de Livros ---
    private static void menuGerenciarLivros() {
        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- Gerenciar Livros ---");
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
                        sistema.adicionarLivro(titulo, autor, isbnAdd);
                        break;
                    case 2: // Remover Livro
                        System.out.print("ISBN do livro a ser removido: ");
                        String isbnRemover = scanner.nextLine();
                        sistema.removerLivro(isbnRemover);
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

    // --- Menu História 7: Gerenciamento de Empréstimos ---
    private static void menuGerenciarEmprestimos() {
        int subOpcao = -1;
        while (subOpcao != 0) {
            System.out.println("\n--- Gerenciar Empréstimos/Devoluções ---");
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
                            sistema.registrarEmprestimo(isbnEmprestimo, idMembro);
                        } else {
                            System.out.println("ERRO: ID do Membro deve ser um número.");
                            scanner.nextLine();
                        }
                        break;
                    case 2: // Registrar Devolução
                        System.out.print("ISBN do Livro a ser devolvido: ");
                        String isbnDevolucao = scanner.nextLine();
                        sistema.registrarDevolucao(isbnDevolucao);
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