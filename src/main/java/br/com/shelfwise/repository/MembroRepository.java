// MODIFICADO: src/main/java/br/com/shelfwise/repository/MembroRepository.java
package br.com.shelfwise.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.shelfwise.domain.Membro;

public class MembroRepository {
    
    private static final String ARQUIVO_DADOS = "membros.dat";
    
    private List<Membro> membros;
    private int proximoMembroId;

    @SuppressWarnings("unchecked")
    public MembroRepository() {
        // Carrega os dados do arquivo
        this.membros = (List<Membro>) PersistenceUtil.carregarDados(ARQUIVO_DADOS);
        
        // Se a lista estiver vazia (primeira execução), cadastra os membros iniciais
        if (this.membros.isEmpty()) {
            System.out.println("Base de membros não encontrada. Criando dados iniciais...");
            adicionarMembroInicial(new Membro(1, "Ana Silva", "ana.silva@cesar.school", "senha123"));
            adicionarMembroInicial(new Membro(2, "Bruno Souza", "bruno.souza@cesar.school", "senha456"));
            // Salva os dados iniciais
            PersistenceUtil.salvarDados(this.membros, ARQUIVO_DADOS);
        }
        
        // Define o próximo ID com base no maior ID existente
        this.proximoMembroId = this.membros.stream()
                                    .mapToInt(Membro::getId)
                                    .max()
                                    .orElse(0) + 1;
    }
    
    private void adicionarMembroInicial(Membro membro) {
        membros.add(membro);
    }

    public Membro adicionar(String nome, String email, String senha) {
        Membro novoMembro = new Membro(proximoMembroId++, nome, email, senha);
        membros.add(novoMembro);
        // Salva os dados após adicionar
        PersistenceUtil.salvarDados(membros, ARQUIVO_DADOS);
        return novoMembro;
    }
    
    public Optional<Membro> buscarPorId(int id) {
        return membros.stream()
            .filter(m -> m.getId() == id)
            .findFirst();
    }
    
    /**
     * História 2: Validação de email
     */
    public Optional<Membro> buscarPorEmail(String email) {
        return membros.stream()
            .filter(m -> m.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }
    
    public List<Membro> listarTodos() {
        return new ArrayList<>(membros);
    }
}