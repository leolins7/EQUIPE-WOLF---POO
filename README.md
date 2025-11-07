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
* **Leonardo Felipe Demétrio Lins Nascimento** 
* **Ramom de Oliveira Aguiar**

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
    User[Usuário (Navegador)] -->|"HTTPS"| CDN[CDN / WAF<br/>CloudFront]
    CDN --> LB[Load Balancer<br/>Distribui Requisições]
    
    subgraph "AWS VPC (Rede Privada)"
        LB --> ASG[Auto Scaling Group<br/>Servidores API ShelfWise]
        
        ASG -->|Leitura Rápida| Cache[(Redis<br/>Cache de Livros)]
        ASG -->|Persistência| DB[(Banco de Dados<br/>PostgreSQL RDS)]
        
        ASG -.->|Logs| Monitor[Monitoramento (CloudWatch)]
    end







