## Pré-requisitos

1. **Docker** e **Docker Compose**: Para rodar os serviços em contêineres.
2. **Git**: Para clonar o repositório.

## Passos para configurar e rodar

### 1. Clone o repositório

```bash
git clone https://github.com/kaufon/autobots.git
cd autobots
```

### 2. Executando a Aplicação

Este modo irá construir as imagens Docker e iniciar todos os serviços.

1.  Abra um terminal na pasta raiz do projeto (onde o arquivo `docker-compose.yml` está localizado).
2.  Execute o seguinte comando:

    ```bash
    docker-compose up --build
    ```
3.  Aguarde a inicialização. Você verá os logs de todos os serviços no seu terminal. O ambiente estará pronto quando você vir as mensagens de `Tomcat started on port(s)` para cada serviço.

### 3. Verificando a Aplicação 

Após iniciar os serviços, o gateway deve estar acessível em `http://localhost:8080`.

