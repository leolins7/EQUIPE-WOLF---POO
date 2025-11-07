/**
 * [DDD] Domain Service
 * Contém regras de negócio que envolvem múltiplas entidades (Livro e Membro),
 * como realizar um empréstimo ou devolução.
 */

package br.com.shelfwise.service;

import java.util.List;
import java.util.Optional;

import br.com.shelfwise.domain.Emprestimo;
import br.com.shelfwise.domain.Livro;
import br.com.shelfwise.domain.Membro;
import br.com.shelfwise.exception.ValidacaoException;
import br.com.shelfwise.repository.EmprestimoRepository;
import br.com.shelfwise.repository.LivroRepository;
import br.com.shelfwise.repository.MembroRepository;

public class BibliotecaService {

    // Injeção de Dependência (os repositórios são "injetados" no serviço)
    private final LivroRepository livroRepository;
    private final MembroRepository membroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public BibliotecaService(LivroRepository livroRepository, MembroRepository membroRepository, EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.membroRepository = membroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    // --- História 1: Pesquisa de Livros ---
    public List<Livro> pesquisarLivros(String termo) {
        return livroRepository.buscarPorTituloContendo(termo);
    }

    // --- História 2: Cadastro de Novo Membro ---
    public Membro cadastrarMembro(String nome, String email, String senha) {
        // Cenário 2.2: Tentativa de cadastro com email inválido
        if (email == null || !email.endsWith("@cesar.school")) {
            throw new ValidacaoException("Email inválido. Deve ser um email institucional (@cesar.school).");
        }
        
        // Regra de negócio: verificar se email já existe
        if (membroRepository.buscarPorEmail(email).isPresent()) {
            throw new ValidacaoException("Este email já está cadastrado.");
        }
        
        // Cenário 2.1: Cadastro com sucesso
        return membroRepository.adicionar(nome, email, senha);
    }

    // --- História 6: Gerenciamento de Livros ---
    public void adicionarLivro(String titulo, String autor, String isbn) {
        // Cenário 6.1: Regra de unicidade de ISBN
        if (livroRepository.buscarPorIsbn(isbn).isPresent()) {
            throw new ValidacaoException("Livro com ISBN " + isbn + " já existe no acervo.");
        }
        Livro novoLivro = new Livro(titulo, autor, isbn);
        livroRepository.adicionar(novoLivro);
    }

    public void removerLivro(String isbn) {
        // Cenário 6.2
        Livro livro = livroRepository.buscarPorIsbn(isbn)
                .orElseThrow(() -> new ValidacaoException("Livro com ISBN " + isbn + " não encontrado."));

        // Regra de negócio: Verifica se o livro está emprestado
        if (!livro.isDisponivel()) {
            throw new ValidacaoException("Não é possível remover o livro. Ele está atualmente emprestado.");
        }
        livroRepository.remover(livro);
    }

    // --- História 7: Gerenciamento de Empréstimos ---
    public void registrarEmprestimo(String isbnLivro, int idMembro) {
        // Cenário 7.1
        Livro livro = livroRepository.buscarPorIsbn(isbnLivro)
                .orElseThrow(() -> new ValidacaoException("Livro não encontrado."));
        
        Membro membro = membroRepository.buscarPorId(idMembro)
                .orElseThrow(() -> new ValidacaoException("Membro não encontrado."));

        // Regra de negócio 1: Verificar disponibilidade
        if (!livro.isDisponivel()) {
            throw new ValidacaoException("Livro '" + livro.getTitulo() + "' não está disponível para empréstimo.");
        }

        // Regra de negócio 2: Limite de empréstimo
        if (membro.getLivrosEmprestadosIsbn().size() >= 3) {
            throw new ValidacaoException("Membro '" + membro.getNome() + "' atingiu o limite de 3 livros emprestados.");
        }

        // Realiza o Empréstimo
        livro.setDisponivel(false);
        membro.adicionarEmprestimo(livro.getIsbn());
        Emprestimo novoEmprestimo = new Emprestimo(livro, membro);
        emprestimoRepository.adicionar(novoEmprestimo);
    }

    public void registrarDevolucao(String isbnLivro) {
        // Cenário 7.2
        Livro livro = livroRepository.buscarPorIsbn(isbnLivro)
                .orElseThrow(() -> new ValidacaoException("Livro com ISBN " + isbnLivro + " não encontrado."));

        if (livro.isDisponivel()) {
            throw new ValidacaoException("Livro '" + livro.getTitulo() + "' já está marcado como disponível.");
        }

        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.buscarAtivoPorIsbn(isbnLivro);
        
        if (emprestimoOpt.isEmpty()) {
            // Se não há registro de empréstimo, mas o livro está indisponível,
            // é uma inconsistência de dados, mas podemos corrigir o status do livro.
            livro.setDisponivel(true);
            throw new ValidacaoException("AVISO: Livro não possuía registro de empréstimo ativo. Status corrigido para 'Disponível'.");
        }
        
        Emprestimo emprestimo = emprestimoOpt.get();
        Membro membro = emprestimo.getMembro();
        
        membro.removerEmprestimo(isbnLivro);
        emprestimoRepository.remover(emprestimo);
        livro.setDisponivel(true);
    }
    
    // --- Métodos Auxiliares para UI (Status) ---
    
    public List<Livro> getStatusAcervo() {
        return livroRepository.listarTodos();
    }
    
    public List<Membro> getStatusMembros() {
        return membroRepository.listarTodos();
    }
}