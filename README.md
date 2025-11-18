
# EQUIPE WOLF - POO
# ShelfWise

### Descrição do Projeto

O ShelfWise é um projeto acadêmico de Programação Orientada a Objetos (POO) desenvolvido para o curso de Análise e Desenvolvimento de Sistemas. Nosso objetivo é criar uma solução completa para o gerenciamento de uma biblioteca, focando em funcionalidades essenciais como o controle do acervo de livros, o cadastro de membros e a automação das transações de empréstimo e devolução. O projeto será construído em Java, aplicando boas práticas de design e um padrão arquitetural que será definido nas próximas etapas.

### Requisitos

O sistema é fundamentado nas seguintes entidades e requisitos, que servirão de base para a arquitetura:

* Linguagem: Java
* Abordagem: Programação Orientada a Objetos (POO)
* Padrão Arquitetural: A definir (Será implementado nas próximas fases)
* Persistência de Dados: A definir (Será implementado nas próximas fases)

### Classes de Domínio

O sistema é construído sobre as seguintes entidades principais:

* **Livro**: Representa um item do acervo, com atributos como título, autor, ISBN e status de disponibilidade.
* **Membro**: Representa o usuário da biblioteca, com atributos como nome, ID e um histórico de empréstimos.
* **Empréstimo**: Representa a transação de um livro para um membro, registrando data, status e os itens da transação.

### Equipe

* **Francisco Ítalo Machado Dantas**
* **João Eduardo Monteiro Cavalcanti**
* **Jonas de Lima Neto**
* **Leonardo Felipe Demétrio Lins Nascimento** * **Ramom de Oliveira Aguiar**

### Cronograma de Entregas

Este projeto será desenvolvido em etapas, conforme o cronograma abaixo:

* **Entrega 01 (26/08 - 29/08):** Kickoff do projeto, definição das histórias de usuário no formato BDD e protótipo Lo-Fi.
* **Entrega 02 (30/09):** Implementação de no mínimo duas histórias, com persistência em memória.
* **Entrega 03 (23/10):** Implementação de mais duas histórias e refatoração para arquitetura em camadas com padrões de projeto.
* **Entrega 04 (17/11):** Implementação de mais três histórias e refatoração para persistência permanente (banco de dados, arquivo, serialização).

### Documentação e Recursos

* **Histórias dos usuários:** Link para o protótipo da interface de usuário.
    - [* [Documento]](https://docs.google.com/document/d/1QKmTRIPZwUpkjAnV5qHJuFJP7M1U7oL6VVkIEZXyEaM/edit?tab=t.0)
* **Protótipo Lo-Fi (Figma):** Link para o protótipo da interface de usuário.
    - [* [Protótipo]](https://www.figma.com/proto/8TUjll3hCDhJth6ovPqqs9/Proto?node-id=1-2&p=f&t=nCocUZmSsx2QcvJk-1&scaling=contain&content-scaling=fixed&page-id=0%3A1)
* **Screencasts:** Link para os vídeos de apresentação do projeto no YouTube.
    - [* [Screencast da Entrega 01]](https://www.youtube.com/watch?v=ZOuNIVcGXiU)     
    - [* [Screencast da Entrega 02]](https://www.youtube.com/watch?v=sI7awY6X_TU)
    - [* [Screencast da Entrega 03]](https://youtu.be/Yn3SnhepH4U)
    - [* [Screencast da Entrega 03 Testes Automatizados]](https://youtu.be/GNe7GJtDsZM)
    - [* [Screencast da Entrega 04]](https://www.youtube.com/watch?v=SsnKRnAHtgM)
    - [* [Screencast da Entrega 04 Testes Automatizados]](https://www.youtube.com/watch?v=i5YQfsOWo48)


### Refatoração para Persistência Permanente (Entrega 04)

O sistema foi refatorado para usar **Serialização de Objetos Java** como método de persistência permanente.

* **Como funciona:** Agora, todos os dados de livros, membros e empréstimos são salvos automaticamente em arquivos binários (`.dat`) na raiz do diretório do projeto.
* **Arquivos Gerados:**
    * `livros.dat`: Armazena a lista de todos os livros do acervo.
    * `membros.dat`: Armazena a lista de membros cadastrados.
    * `emprestimos.dat`: Armazena a lista de todos os empréstimos ativos.
* **Garantia:** Isso garante que os dados persistam entre as execuções do programa. Ao iniciar o sistema, os dados são lidos desses arquivos, e qualquer alteração (como adicionar um livro ou registrar um empréstimo) é salva imediatamente no arquivo correspondente.


### ISSUE/BUG Tracker

<img width="1293" height="390" alt="image" src="https://github.com/user-attachments/assets/2c24268c-4b92-4142-b636-a1af7c96b4b5" />

<img width="948" height="636" alt="image" src="https://github.com/user-attachments/assets/333734ef-9d94-4855-a5e3-22ade0e83298" />

<img width="969" height="674" alt="image" src="https://github.com/user-attachments/assets/32a2c144-6bb1-437f-9670-927d27365f34" />


### ISSUE/BUG Tracker Entrega 03:

<img width="1230" height="322" alt="image" src="https://github.com/user-attachments/assets/c5bd9eb1-ae2f-473a-92d8-60bf7afa9086" />

<img width="961" height="701" alt="image" src="https://github.com/user-attachments/assets/c5213515-cdfc-4ba5-ad1c-6900cdae106a" />

<img width="965" height="769" alt="image" src="https://github.com/user-attachments/assets/60f55c8f-9c91-428d-9978-b46e95de8bf1" />

<img width="934" height="732" alt="image" src="https://github.com/user-attachments/assets/9b21511a-8ba8-460a-9d59-a3d3aef6c697" />

<img width="941" height="772" alt="image" src="https://github.com/user-attachments/assets/4613210f-e47e-407e-bfcc-c9953a690e7a" />

### ISSUE/BUG Tracker Entrega 04:

<img width="1228" height="315" alt="image" src="https://github.com/user-attachments/assets/1c31e5b0-c3d3-4047-820d-9a558adec7e0" />


## 🚀 Como Executar o Projeto

Esta seção guia você na montagem do ambiente e execução da aplicação ShelfWise.

### Pré-requisitos

Para compilar e executar o projeto localmente, você precisará ter instalado:

  * **Git:** Para clonar o repositório.
  * **Java JDK 17:** O projeto está configurado para usar o Java 17.
  * **Apache Maven 3.8+:** Para gerenciamento de dependências e build da aplicação.
  * **(Opcional) Docker:** Para executar a aplicação em um contêiner.

-----

### Opção 1: Executando Localmente (Via Maven)

Este é o método padrão para desenvolvimento e teste.

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/leolins7/equipe-wolf---poo.git
    cd equipe-wolf---poo
    ```

2.  **Compile o projeto e gere o `.jar`:**
    Este comando irá compilar o código, rodar os testes e empacotar a aplicação em um arquivo `.jar` executável dentro da pasta `target/`.

    ```bash
    mvn clean package
    ```

3.  **Execute a aplicação:**
    Após o build, execute o arquivo `.jar` gerado.

    ```bash
    java -jar target/equipewolf-poo-1.0-SNAPSHOT.jar
    ```

O menu principal da aplicação aparecerá no seu terminal.

-----

### Opção 2: Executando com Docker (Recomendado para Simplicidade)

Se você possui Docker, pode executar a aplicação sem se preocupar em instalar o Java ou Maven na sua máquina, pois o `Dockerfile` cuida de tudo.

1.  **Construa a imagem Docker:**
    Na raiz do projeto (onde está o `Dockerfile`), execute:

    ```bash
    docker build -t shelfwise .
    ```

2.  **Execute o contêiner:**
    Use `-it` para rodar em modo interativo, permitindo que você use o menu do console:

    ```bash
    docker run -it shelfwise
    ```

-----

### Executando os Testes Automatizados

Para rodar apenas a suíte de testes unitários (JUnit/Mockito), utilize o comando Maven:

```bash
mvn test
```

### Sobre a Persistência de Dados

Ao executar a aplicação, serão criados arquivos (`livros.dat`, `membros.dat`, `emprestimos.dat`) na raiz do diretório. Este é o comportamento esperado, pois o sistema utiliza serialização de objetos para persistência permanente.


## 🚀 Planejamento de Evolução (Trabalho Acadêmico)

Esta seção documenta a pesquisa de arquitetura e hospedagem para a evolução do ShelfWise para um sistema Web.

### 1. Hospedagem Gratuita (Requisito 1)
Pesquisa de opções para deploy de um MVP (Minimum Viável Product) gratuito:
- **Frontend (Web):** Vercel ou Netlify.
- **Backend (API Java):** Render ou Railway.
- **Banco de Dados:** Neon.tech (PostgreSQL).

### 2. Arquitetura Alvo na Nuvem (Requisito 2)
Arquitetura sugerida para garantir escalabilidade, segurança e robustez (tráfego baixo a moderado):

```mermaid
graph TD
    User[Usuario - Navegador] --> HTTPS[Conexao HTTPS]
    HTTPS --> CDN[CDN / WAF<br>CloudFront]
    CDN --> LB[Load Balancer<br>Distribui Requisicoes]
    
    subgraph AWS_VPC_Rede_Privada
        LB --> ASG[Auto Scaling Group<br>Servidores API ShelfWise]
        
        ASG --> Cache[(Redis<br>Cache de Livros)]
        ASG --> DB[(Banco de Dados<br>PostgreSQL RDS)]
        
        ASG -.-> Monitor[Monitoramento CloudWatch]
    end
