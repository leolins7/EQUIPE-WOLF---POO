// MODIFICADO: src/main/java/br/com/shelfwise/repository/LivroRepository.java
package br.com.shelfwise.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.shelfwise.domain.Livro;

public class LivroRepository {
    
    // Arquivo de persistência
    private static final String ARQUIVO_DADOS = "livros.dat";
    
    // Persistência em memória, não mais estática
    private List<Livro> acervo;

    @SuppressWarnings("unchecked")
    public LivroRepository() {
        // Carrega os dados do arquivo ao iniciar
        this.acervo = (List<Livro>) PersistenceUtil.carregarDados(ARQUIVO_DADOS);
    }

    public void adicionar(Livro livro) {
        acervo.add(livro);
        // Salva os dados após a modificação
        PersistenceUtil.salvarDados(acervo, ARQUIVO_DADOS);
    }
    
    public void remover(Livro livro) {
        acervo.remove(livro);
        // Salva os dados após a modificação
        PersistenceUtil.salvarDados(acervo, ARQUIVO_DADOS);
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