# Como executar o projeto

## Pré-requisitos

- Docker e Docker Compose
- Java (versão compatível com o projeto)
- Maven

## Passo 1: Iniciar o banco de dados

Na raiz do projeto, execute o comando abaixo para subir o container do MariaDB:

```bash
docker compose up -d
```

## Passo 2: Compilar o projeto

Execute o comando abaixo em ```/IFBA/INF008/microkernel``` para recompilar o projeto, empacotar os plugins em arquivos JAR e iniciar a aplicação:

```bash
mvn install exec:java -pl app
```

## Resumo

```bash
docker compose up -d
mvn install exec:java -pl app
```
