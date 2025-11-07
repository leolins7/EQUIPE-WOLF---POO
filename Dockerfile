# Usamos uma imagem base com Java 17 e Maven para compilar
# Isso garante que quem for rodar não precisa ter Java/Maven instalados na máquina
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
# Baixa as dependências primeiro (aproveita cache do Docker)
RUN mvn dependency:go-offline
COPY src ./src
# Compila o projeto e gera o .jar
RUN mvn clean package -DskipTests

# --- Segundo estágio: Apenas roda a aplicação ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia apenas o .jar gerado no estágio anterior
COPY --from=build /app/target/equipewolf-poo-1.0-SNAPSHOT.jar app.jar
# Comando para rodar sua aplicação console
ENTRYPOINT ["java", "-jar", "app.jar"]