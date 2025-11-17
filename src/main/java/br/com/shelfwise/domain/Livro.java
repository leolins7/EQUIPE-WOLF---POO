// MODIFICADO: src/main/java/br/com/shelfwise/domain/Livro.java
/**
 * [DDD] Entity
 * Entidade que possui ciclo de vida e identidade própria (ISBN).
 */
package br.com.shelfwise.domain;

// Adicionado java.io.Serializable
public class Livro implements java.io.Serializable {
    
    // Boa prática para serialização
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel;

    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true; // Novo livro sempre começa disponível
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // Setter
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format("Livro [Título: %s, Autor: %s, ISBN: %s, Status: %s]",
                titulo, autor, isbn, disponivel ? "Disponível" : "Emprestado");
    }
}