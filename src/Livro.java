public class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponivel; // Se está na prateleira (true) ou emprestado/reservado (false)
    
    // Construtor
    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true; // Por padrão, o livro é adicionado como disponível
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
    
    // Setters para alteração de status
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    @Override
    public String toString() {
        return String.format("Livro [Título: %s, Autor: %s, ISBN: %s, Status: %s]",
                             titulo, autor, isbn, disponivel ? "Disponível" : "Emprestado");
    }
}