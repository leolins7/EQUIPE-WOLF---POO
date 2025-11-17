// MODIFICADO: src/main/java/br/com/shelfwise/repository/EmprestimoRepository.java
package br.com.shelfwise.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.shelfwise.domain.Emprestimo;

public class EmprestimoRepository {
    
    private static final String ARQUIVO_DADOS = "emprestimos.dat";
    
    private List<Emprestimo> emprestimosAtivos;
    
    @SuppressWarnings("unchecked")
    public EmprestimoRepository() {
        // Carrega os dados do arquivo
        this.emprestimosAtivos = (List<Emprestimo>) PersistenceUtil.carregarDados(ARQUIVO_DADOS);
    }
    
    public void adicionar(Emprestimo emprestimo) {
        emprestimosAtivos.add(emprestimo);
        // Salva os dados
        PersistenceUtil.salvarDados(emprestimosAtivos, ARQUIVO_DADOS);
    }
    
    public void remover(Emprestimo emprestimo) {
        emprestimosAtivos.remove(emprestimo);
        // Salva os dados
        PersistenceUtil.salvarDados(emprestimosAtivos, ARQUIVO_DADOS);
    }
    
    public Optional<Emprestimo> buscarAtivoPorIsbn(String isbn) {
         return emprestimosAtivos.stream()
            .filter(e -> e.getLivro().getIsbn().equals(isbn))
            .findFirst();
    }
    
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimosAtivos);
    }
}