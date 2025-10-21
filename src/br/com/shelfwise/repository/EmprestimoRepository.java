package br.com.shelfwise.repository;

import br.com.shelfwise.domain.Emprestimo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoRepository {
    
    private static List<Emprestimo> emprestimosAtivos = new ArrayList<>();
    
    public void adicionar(Emprestimo emprestimo) {
        emprestimosAtivos.add(emprestimo);
    }
    
    public void remover(Emprestimo emprestimo) {
        emprestimosAtivos.remove(emprestimo);
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