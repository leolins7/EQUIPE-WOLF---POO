import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaBiblioteca {
    // Persistência em Memória
    private List<Livro> acervo;
    private List<Membro> membros;
    private List<Emprestimo> emprestimosAtivos;
    private int proximoMembroId = 1; // Para gerar IDs de Membro

    public SistemaBiblioteca() {
        this.acervo = new ArrayList<>();
        this.membros = new ArrayList<>();
        this.emprestimosAtivos = new ArrayList<>();
    }
    
    // --- História 6: Gerenciamento de Livros ---

    /**
     * Cenário 6.1: Adicionar um novo livro
     */
    public void adicionarLivro(String titulo, String autor, String isbn) {
        // Simples verificação de unicidade de ISBN
        boolean existe = acervo.stream().anyMatch(l -> l.getIsbn().equals(isbn));
        if (existe) {
            System.out.println("ERRO: Livro com ISBN " + isbn + " já existe no acervo.");
            return;
        }
        Livro novoLivro = new Livro(titulo, autor, isbn);
        acervo.add(novoLivro);
        System.out.println("SUCESSO: Livro '" + titulo + "' adicionado ao acervo.");
    }

    /**
     * Cenário 6.2: Remover um livro
     */
    public void removerLivro(String isbn) {
        Optional<Livro> livroParaRemover = acervo.stream()
            .filter(l -> l.getIsbn().equals(isbn))
            .findFirst();
            
        if (livroParaRemover.isPresent()) {
            // Verifica se o livro está emprestado antes de remover
            if (!livroParaRemover.get().isDisponivel()) {
                System.out.println("ERRO: Não é possível remover o livro. Ele está atualmente emprestado.");
                return;
            }
            acervo.remove(livroParaRemover.get());
            System.out.println("SUCESSO: Livro com ISBN " + isbn + " removido do acervo.");
        } else {
            System.out.println("ERRO: Livro com ISBN " + isbn + " não encontrado.");
        }
    }
    
    // Método auxiliar para adicionar um membro para os testes da História 7
    public void adicionarMembro(String nome, String email) {
        Membro novoMembro = new Membro(proximoMembroId++, nome, email);
        membros.add(novoMembro);
        System.out.println("SUCESSO: Membro " + nome + " cadastrado com ID " + novoMembro.getId());
    }
    
    // --- História 7: Gerenciamento de Empréstimos ---

    /**
     * Cenário 7.1: Registrar empréstimo
     */
    public void registrarEmprestimo(String isbnLivro, int idMembro) {
        Optional<Livro> livroOpt = acervo.stream()
            .filter(l -> l.getIsbn().equals(isbnLivro))
            .findFirst();
        
        Optional<Membro> membroOpt = membros.stream()
            .filter(m -> m.getId() == idMembro)
            .findFirst();

        if (livroOpt.isEmpty() || membroOpt.isEmpty()) {
            System.out.println("ERRO: Livro ou Membro não encontrado.");
            return;
        }

        Livro livro = livroOpt.get();
        Membro membro = membroOpt.get();

        // 1. Verificar disponibilidade
        if (!livro.isDisponivel()) {
            System.out.println("ERRO: Livro '" + livro.getTitulo() + "' não está disponível para empréstimo.");
            return;
        }
        
        // Simulação de limite de empréstimo (Regra de Negócio)
        if (membro.getLivrosEmprestadosIsbn().size() >= 3) {
            System.out.println("ERRO: Membro '" + membro.getNome() + "' atingiu o limite de 3 livros emprestados.");
            return;
        }

        // 2. Realiza o Empréstimo
        livro.setDisponivel(false);
        membro.adicionarEmprestimo(livro.getIsbn());
        Emprestimo novoEmprestimo = new Emprestimo(livro, membro);
        emprestimosAtivos.add(novoEmprestimo);
        
        System.out.println("SUCESSO: Empréstimo de '" + livro.getTitulo() + "' para " + membro.getNome() + " registrado.");
    }
    
    /**
     * Cenário 7.2: Registrar devolução
     */
    public void registrarDevolucao(String isbnLivro) {
        Optional<Livro> livroOpt = acervo.stream()
            .filter(l -> l.getIsbn().equals(isbnLivro))
            .findFirst();
        
        if (livroOpt.isEmpty()) {
            System.out.println("ERRO: Livro com ISBN " + isbnLivro + " não encontrado.");
            return;
        }
        
        Livro livro = livroOpt.get();
        
        if (livro.isDisponivel()) {
            System.out.println("AVISO: Livro '" + livro.getTitulo() + "' já está marcado como disponível.");
            return;
        }
        
        // 1. Remove o registro de empréstimo ativo
        Optional<Emprestimo> emprestimoOpt = emprestimosAtivos.stream()
            .filter(e -> e.getLivro().getIsbn().equals(isbnLivro))
            .findFirst();
        
        if (emprestimoOpt.isEmpty()) {
            System.out.println("AVISO: Livro não possui um registro de empréstimo ativo. Corrigindo status.");
            // Continua para corrigir o status do livro
        } else {
            Emprestimo emprestimo = emprestimoOpt.get();
            Membro membro = emprestimo.getMembro();
            
            // 2. Remove o livro da lista de empréstimos do membro
            membro.removerEmprestimo(isbnLivro);
            
            // 3. Remove o empréstimo da lista de ativos
            emprestimosAtivos.remove(emprestimo);
        }

        // 4. Atualiza o status do livro
        livro.setDisponivel(true);
        System.out.println("SUCESSO: Livro '" + livro.getTitulo() + "' devolvido e agora está disponível.");
    }
    
    // Método auxiliar para listar o estado atual do sistema
    public void statusAcervo() {
        System.out.println("\n--- STATUS DO ACERVO ---");
        if (acervo.isEmpty()) {
             System.out.println("O acervo está vazio.");
             return;
        }
        acervo.forEach(System.out::println);
        System.out.println("------------------------");
    }
}