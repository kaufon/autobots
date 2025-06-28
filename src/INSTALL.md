# Tutorial de Instalação e Execução do Projeto Spring Boot

## Pré-requisitos
Certifique-se de que você tenha as seguintes ferramentas instaladas no sistema:
- [Java JDK 21+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

## Passo 1: Clonar o repositório
Abra o terminal e execute o seguinte comando para clonar o repositório:
```bash
git clone https://github.com/kaufon/autobots-4.git
cd autobots-4
```

## Passo 2: Configurar o ambiente
Certifique-se de que o Java e o Maven estejam configurados corretamente. Para verificar, execute:
```bash
java -version
mvn -version
```

## Passo 3: Importar o projeto
Abra o projeto em sua IDE preferida, como [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/).

### Importar no IntelliJ IDEA
1. Selecione `File > Open`.
2. Navegue até o diretório do projeto e clique em `Open`.
3. Aguarde o IntelliJ importar o projeto Maven.

### Importar no Eclipse
1. Selecione `File > Import > Existing Maven Projects`.
2. Navegue até o diretório do projeto e clique em `Finish`.

## Passo 4: Configurar o banco de dados
Este projeto utiliza um banco de dados H2 embutido. Não é necessário configurar um banco de dados externo.

## Passo 5: Executar o projeto
### Executar pela IDE
1. Localize a classe principal do projeto: `AutomanagerApplication`.
2. Clique com o botão direito na classe e selecione `Run`.

### Executar pelo terminal
1. Navegue até o diretório do projeto:
```bash
cd autobots-4
```
2. Execute o seguinte comando para rodar o projeto:
```bash
mvn spring-boot:run
```

## Passo 6: Testar a aplicação
Abra seu navegador ou ferramenta REST como [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/).

### Endereço base
A aplicação estará disponível em:
```
http://localhost:8080
```

### Exemplos de endpoints
- **Listar usuários:**
```
GET http://localhost:8080/usuario/listar
```
- **Login:**
```
POST http://localhost:8080/autenticacao/login
Content-Type: application/json
{
  "nomeUsuario": "gerente-usuario",
  "senha": "gerente2025"
}
```

