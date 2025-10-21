package br.com.shelfwise.repository;

import br.com.shelfwise.domain.Membro;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MembroRepository {
    
    private static List<Membro> membros = new ArrayList<>();
    private static int proximoMembroId = 1;

    // Dados iniciais para teste (para não ter que cadastrar toda vez)
    static {
        adicionarMembroInicial(new Membro(proximoMembroId++, "Ana Silva", "ana.silva@cesar.school", "senha123"));
        adicionarMembroInicial(new Membro(proximoMembroId++, "Bruno Souza", "bruno.souza@cesar.school", "senha456"));
    }
    
    private static void adicionarMembroInicial(Membro membro) {
        membros.add(membro);
    }

    public Membro adicionar(String nome, String email, String senha) {
        Membro novoMembro = new Membro(proximoMembroId++, nome, email, senha);
        membros.add(novoMembro);
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