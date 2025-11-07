/**
 * [DDD] Aggregate Root
 * Entidade que representa o usuário da biblioteca.
 * Gerencia sua própria lista de empréstimos para garantir consistência.
 */

package br.com.shelfwise.domain;

import java.util.ArrayList;
import java.util.List;

public class Membro {
    private int id;
    private String nome;
    private String email; // Adicionado para História 2
    private String senha; // Adicionado para História 2 (embora não usado em lógica de login ainda)
    private List<String> livrosEmprestadosIsbn;

    // Construtor atualizado
    public Membro(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha; // Armazena a senha
        this.livrosEmprestadosIsbn = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }

    public List<String> getLivrosEmprestadosIsbn() {
        return livrosEmprestadosIsbn;
    }

    // Métodos de negócio
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