# Estágio 1: Build (Usando uma imagem com JDK 25)
FROM eclipse-temurin:25-jdk-noble AS build
WORKDIR /app

# Copia os arquivos de configuração do Gradle primeiro (aproveita o cache de camadas)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Dá permissão de execução e baixa as dependências
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# Copia o código fonte e gera o JAR
COPY src src
RUN ./gradlew bootJar --no-daemon

# Estágio 2: Runtime (Imagem final, muito mais leve)
FROM eclipse-temurin:25-jre-noble
WORKDIR /app

# Copia apenas o JAR gerado no estágio anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]