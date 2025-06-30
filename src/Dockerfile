# =========================================================================
# ESTÁGIO 1: O CONSTRUTOR (Builder)
# =========================================================================
# AQUI: A linha deve usar a tag 'jammy'
FROM eclipse-temurin:21-jdk-jammy AS builder

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia os arquivos de configuração do Maven.
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .
# Garante que o script tenha permissão de execução
RUN chmod +x ./mvnw

# Baixa as dependências
RUN ./mvnw dependency:go-offline

# Copia o restante do código-fonte da aplicação.
COPY src/ ./src/

# Executa o comando para "empacotar" a aplicação, gerando o arquivo .jar.
RUN ./mvnw package -DskipTests

# =========================================================================
# ESTÁGIO 2: A IMAGEM FINAL (Runner)
# =========================================================================
# E AQUI TAMBÉM: A linha deve usar a tag 'jammy'
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho.
WORKDIR /app

# Copia apenas o arquivo .jar gerado no estágio 'builder' para a imagem final.
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão do Spring Boot.
EXPOSE 8080

# Define o comando que será executado quando o contêiner iniciar.
ENTRYPOINT ["java", "-jar", "app.jar"]
