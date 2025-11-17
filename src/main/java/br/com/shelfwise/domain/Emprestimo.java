// MODIFICADO: src/main/java/br/com/shelfwise/domain/Emprestimo.java
package br.com.shelfwise.domain;

import java.time.LocalDate;

// Adicionado java.io.Serializable
public class Emprestimo implements java.io.Serializable {

    // Boa prática para serialização
    private static final long serialVersionUID = 1L;

    private Livro livro;
    private Membro membro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;

    public Emprestimo(Livro livro, Membro membro) {
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevistaDevolucao = this.dataEmprestimo.plusDays(14);
    }

    /**
     * Construtor usado especificamente para testes automatizados,
     * permitindo injetar uma data de empréstimo específica.
     */
    public Emprestimo(Livro livro, Membro membro, LocalDate dataEmprestimo) {
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = this.dataEmprestimo.plusDays(14);
    }

    // Getters
    public Livro getLivro() {
        return livro;
    }

    public Membro getMembro() {
        return membro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    
    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    @Override
    public String toString() {
        return String.format("Empréstimo [Livro: %s, Membro: %s, Data: %s, Devolução Prevista: %s]",
                livro.getTitulo(), membro.getNome(), dataEmprestimo, dataPrevistaDevolucao);
    }
}