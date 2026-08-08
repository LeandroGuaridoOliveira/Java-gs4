# Build da aplicacao a partir da pasta raiz
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY mercado-express/pom.xml ./mercado-express/
COPY mercado-express/src ./mercado-express/src
WORKDIR /app/mercado-express
RUN mvn clean package -DskipTests

# Imagem final de execucao
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/mercado-express/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
