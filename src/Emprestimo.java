import java.time.LocalDate;

public class Emprestimo {
    private Livro livro;
    private Membro membro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    
    // Construtor
    public Emprestimo(Livro livro, Membro membro) {
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = LocalDate.now();
        // Exemplo: Devolução em 14 dias
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
    
    @Override
    public String toString() {
        return String.format("Empréstimo [Livro: %s, Membro: %s, Data: %s, Devolução Prevista: %s]",
                             livro.getTitulo(), membro.getNome(), dataEmprestimo, dataPrevistaDevolucao);
    }
}