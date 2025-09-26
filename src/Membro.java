import java.util.ArrayList;
import java.util.List;

public class Membro {
    private int id;
    private String nome;
    private String email;
    private List<String> livrosEmprestadosIsbn; // Lista de ISBNs dos livros emprestados

    // Construtor
    public Membro(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.livrosEmprestadosIsbn = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getLivrosEmprestadosIsbn() {
        return livrosEmprestadosIsbn;
    }
    
    // Método para adicionar/remover empréstimo
    public void adicionarEmprestimo(String isbn) {
        this.livrosEmprestadosIsbn.add(isbn);
    }
    
    public void removerEmprestimo(String isbn) {
        this.livrosEmprestadosIsbn.remove(isbn);
    }
    
    @Override
    public String toString() {
        return String.format("Membro [ID: %d, Nome: %s, Email: %s, Livros Emprestados: %d]", 
                             id, nome, email, livrosEmprestadosIsbn.size());
    }
}