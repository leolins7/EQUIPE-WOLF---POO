package br.com.shelfwise.repository;

import br.com.shelfwise.domain.Livro;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LivroRepository {
    
    // Persistência em memória
    private static List<Livro> acervo = new ArrayList<>();

    public void adicionar(Livro livro) {
        acervo.add(livro);
    }
    
    public void remover(Livro livro) {
        acervo.remove(livro);
    }
    
    public Optional<Livro> buscarPorIsbn(String isbn) {
        return acervo.stream()
            .filter(l -> l.getIsbn().equals(isbn))
            .findFirst();
    }
    
    public List<Livro> listarTodos() {
        return new ArrayList<>(acervo); // Retorna uma cópia para evitar modificação externa
    }
    
    /**
     * História 1.1: Pesquisa com sucesso
     */
    public List<Livro> buscarPorTituloContendo(String termo) {
        String termoBusca = termo.toLowerCase();
        return acervo.stream()
            .filter(l -> l.getTitulo().toLowerCase().contains(termoBusca))
            .collect(Collectors.toList());
    }
}