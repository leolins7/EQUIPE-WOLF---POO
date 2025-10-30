package br.com.shelfwise.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.shelfwise.domain.Livro;
import br.com.shelfwise.domain.Membro;
import br.com.shelfwise.exception.ValidacaoException;
import br.com.shelfwise.repository.EmprestimoRepository;
import br.com.shelfwise.repository.LivroRepository;
import br.com.shelfwise.repository.MembroRepository;


@ExtendWith(MockitoExtension.class)
class BibliotecaServiceTest {

    // Cria "mocks" (simulações) dos repositórios.
    // Não vamos usar o repositório real, pois queremos testar
    // apenas a lógica de negócio do serviço.
    @Mock
    private LivroRepository livroRepository;
    @Mock
    private MembroRepository membroRepository;
    @Mock
    private EmprestimoRepository emprestimoRepository;

    // Injeta os "mocks" acima dentro da instância do serviço.
    @InjectMocks
    private BibliotecaService bibliotecaService;

    private Livro livroMock;
    private Membro membroMock;

    @BeforeEach
    void setUp() {
        // Configuração padrão para alguns mocks
        livroMock = new Livro("Java Efetivo", "Joshua Bloch", "123");
        membroMock = new Membro(1, "Ana Silva", "ana.silva@cesar.school", "senha123");
    }

    // --- História 1: Pesquisa de Livros ---

    @Test
    @DisplayName("Cenário 1.1: Deve retornar lista de livros que correspondem à pesquisa")
    void pesquisarLivros_CenarioSucesso() {
        // Arrange (Dado que)
        String termo = "Java";
        when(livroRepository.buscarPorTituloContendo(termo)).thenReturn(List.of(livroMock));

        // Act (Quando)
        List<Livro> resultados = bibliotecaService.pesquisarLivros(termo);

        // Assert (Então)
        assertFalse(resultados.isEmpty());
        assertEquals(1, resultados.size());
        assertEquals("Java Efetivo", resultados.get(0).getTitulo());
    }

    @Test
    @DisplayName("Cenário 1.2: Deve retornar lista vazia quando pesquisa não encontra resultados")
    void pesquisarLivros_CenarioSemResultado() {
        // Arrange
        String termo = "Python";
        when(livroRepository.buscarPorTituloContendo(termo)).thenReturn(List.of()); // Retorna lista vazia

        // Act
        List<Livro> resultados = bibliotecaService.pesquisarLivros(termo);

        // Assert
        assertTrue(resultados.isEmpty());
    }

    // --- História 2: Cadastro de Novo Membro ---

    @Test
    @DisplayName("Cenário 2.1: Deve cadastrar membro com email @cesar.school")
    void cadastrarMembro_CenarioSucesso() {
        // Arrange
        String nome = "Novo Aluno";
        String email = "novo.aluno@cesar.school";
        String senha = "senhaNova";
        
        // Simula que não há duplicatas
        when(membroRepository.buscarPorEmail(email)).thenReturn(Optional.empty()); 
        // Simula a ação de adicionar, retornando o membro mockado
        when(membroRepository.adicionar(nome, email, senha)).thenReturn(new Membro(2, nome, email, senha));

        // Act
        Membro membroSalvo = bibliotecaService.cadastrarMembro(nome, email, senha);

        // Assert
        assertNotNull(membroSalvo);
        assertEquals(email, membroSalvo.getEmail());
        // Verifica se o método "adicionar" do repositório foi chamado 1 vez
        verify(membroRepository, times(1)).adicionar(nome, email, senha);
    }

    @Test
    @DisplayName("Cenário 2.2: Deve lançar exceção para email inválido (sem @cesar.school)")
    void cadastrarMembro_DeveLancarExcecao_QuandoEmailForInvalido() {
        // Arrange
        String emailInvalido = "aluno@gmail.com";

        // Act & Assert
        // Verifica se a exceção específica (ValidacaoException) é lançada
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            bibliotecaService.cadastrarMembro("Aluno", emailInvalido, "123");
        });
        
        // Verifica a mensagem de erro
        assertEquals("Email inválido. Deve ser um email institucional (@cesar.school).", exception.getMessage());
        
        // Garante que o método "adicionar" NUNCA foi chamado
        verify(membroRepository, never()).adicionar(any(), any(), any());
    }
    
    @Test
    @DisplayName("Regra de Negócio: Deve lançar exceção para email duplicado")
    void cadastrarMembro_DeveLancarExcecao_QuandoEmailDuplicado() {
        // Arrange
        String emailExistente = "ana.silva@cesar.school";
        // Simula que o email já existe no repositório
        when(membroRepository.buscarPorEmail(emailExistente)).thenReturn(Optional.of(membroMock));

        // Act & Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            bibliotecaService.cadastrarMembro("Outra Ana", emailExistente, "456");
        });
        
        assertEquals("Este email já está cadastrado.", exception.getMessage());
        verify(membroRepository, never()).adicionar(any(), any(), any());
    }

    // --- História 6: Gerenciamento de Livros ---

    @Test
    @DisplayName("Cenário 6.1 (Regra): Deve lançar exceção ao adicionar livro com ISBN duplicado")
    void adicionarLivro_DeveLancarExcecao_QuandoIsbnDuplicado() {
        // Arrange
        String isbnExistente = "123";
        // Simula que o livro já existe
        when(livroRepository.buscarPorIsbn(isbnExistente)).thenReturn(Optional.of(livroMock));
        
        // Act & Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            bibliotecaService.adicionarLivro("Outro Livro de Java", "Autor", isbnExistente);
        });
        
        assertTrue(exception.getMessage().contains("já existe no acervo"));
        verify(livroRepository, never()).adicionar(any());
    }
    
    // --- História 7: Gerenciamento de Empréstimos ---

    @Test
    @DisplayName("Cenário 7.1 (Regra): Deve lançar exceção quando membro atingiu limite de 3 livros")
    void registrarEmprestimo_DeveLancarExcecao_QuandoMembroAtingiuLimite() {
        // Arrange
        // Adiciona 3 empréstimos falsos ao membro mockado
        membroMock.adicionarEmprestimo("ISBN-A");
        membroMock.adicionarEmprestimo("ISBN-B");
        membroMock.adicionarEmprestimo("ISBN-C");
        
        when(livroRepository.buscarPorIsbn(livroMock.getIsbn())).thenReturn(Optional.of(livroMock));
        when(membroRepository.buscarPorId(membroMock.getId())).thenReturn(Optional.of(membroMock));
        
        // Act & Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            bibliotecaService.registrarEmprestimo(livroMock.getIsbn(), membroMock.getId());
        });
        
        assertTrue(exception.getMessage().contains("atingiu o limite de 3 livros emprestados"));
        verify(emprestimoRepository, never()).adicionar(any());
    }
    
    @Test
    @DisplayName("Cenário 7.1 (Regra): Deve lançar exceção ao tentar emprestar livro indisponível")
    void registrarEmprestimo_DeveLancarExcecao_QuandoLivroIndisponivel() {
        // Arrange
        livroMock.setDisponivel(false); // Seta o livro como indisponível
        
        when(livroRepository.buscarPorIsbn(livroMock.getIsbn())).thenReturn(Optional.of(livroMock));
        when(membroRepository.buscarPorId(membroMock.getId())).thenReturn(Optional.of(membroMock));
        
        // Act & Assert
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            bibliotecaService.registrarEmprestimo(livroMock.getIsbn(), membroMock.getId());
        });
        
        assertTrue(exception.getMessage().contains("não está disponível para empréstimo"));
        verify(emprestimoRepository, never()).adicionar(any());
    }
}