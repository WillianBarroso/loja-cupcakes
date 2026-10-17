# Estágio 1: Build da aplicação com Gradle 8.14+
FROM gradle:8.14-jdk17 AS build
WORKDIR /app

# Copia os ficheiros do Gradle e o código-fonte
COPY build.gradle settings.gradle /app/
COPY src /app/src

# Executa o build para gerar o JAR
RUN gradle bootJar -x test --no-daemon

# Estágio 2: Imagem final para execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]