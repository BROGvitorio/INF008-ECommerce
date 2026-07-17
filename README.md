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

## Passo 2: Acessar o banco de dados (opcional)

Caso seja necessário executar consultas SQL ou verificar os dados diretamente no banco, acesse o cliente do MariaDB que já está instalado no container.

### Entrar no cliente MariaDB

```bash
docker exec -it inf008-ecommerce-mariadb mariadb -u inf008 -p ecommerce_inf008
```

Quando for solicitada a senha, informe: ```inf008```

Após a autenticação, o terminal exibirá o prompt do MariaDB:

```sql
MariaDB [ecommerce_inf008]>
```

A partir daí é possível executar comandos SQL normalmente, por exemplo:

```sql
SHOW TABLES;
SELECT * FROM nome_da_tabela;
```

## Passo 3: Compilar e executar o projeto

Execute o comando abaixo em `IFBA/INF008-ECommerce/microkernel` para compilar o projeto, empacotar os plugins em arquivos JAR e iniciar a aplicação:

```bash
mvn install && mvn exec:java -pl app
```
