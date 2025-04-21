# Sistema Gerenciador de Biblioteca - Library Management System (LMS)

Projeto desenvolvido como atividade avaliativa da disciplina Programação Orientada a Objetos (INF008) do curso de Análise e Desenvolvimento de Sistemas do IFBA (3º semestre).
Implementa o sistema de uma biblioteca, permitindo o cadastro de usuários e livros e transações de empréstimo. Possui suporte para gerenciamento de plug-ins em tempo de execução.

## Funcionalidades

- **Cadastro de Usuários e Login**:
    - O sistema permite que o usuário crie uma conta e faça login com seu **e-mail** e **senha**.
- **Gerenciamento de Livros**:
    - O sistema permite o cadastro de livros.
- **Transações de Empréstimo:**
    - O sistema gerencia operações de empréstimo e devolução de livros
- **Relatórios de Empréstimo**:
    - Usuários podem acessar relatórios de todos os livros emprestados no momento e de livros com atraso na devolução com suas multas correspondentes.
    - Essa funcionalidade é implementada como plug-in.
- **Interface de Usuário**:
    - O sistema apresenta uma Interface Gráfica de Usuário (GUI) que permite que os usuários realizem todas as operações descritas.

## Tecnologias Utilizadas
Este projeto foi desenvolvido na linguagem de programação **Java** na versão **OpenJDK 21.0.5 LTS** (Long-Term Support). A persistência dos dados foi feita por meio da **serialização** e **desserialização** com a criação do arquivo `lms.bin`. A GUI foi feita com o **JavaFX**.

## Requisitos
- **Java 8** ou superior (recomendado usar a versão mais recente com suporte LTS);
- **JavaFX**;
- **Maven**;

## Instalação e Execução local
- Faça o download da pasta zipada `LMS-Library-Management-System` e descompacte a pasta usando a ferramenta de sua preferência;
- Abra o **Prompt de Comando** e navegue até o diretório onde a pasta foi descompactada;
- Faça a instalação do projeto com o comando abaixo:
```bash
  mvn install
```
- Execute o programa com o comando:
```bash
  mvn exec:java -pl app
```

## Desenvolvido por:
  Ana Ruth Bezerra

## Contato
  an.bezerra@gmail.com
