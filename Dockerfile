# Fase 1: Build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiamo il pom.xml e scarichiamo le dipendenze (utile per la cache di Docker)
COPY calcio/pom.xml .
RUN mvn dependency:go-offline

# Copiamo il codice sorgente
COPY calcio/src ./src

# Compiliamo il progetto saltando i test per velocizzare il deploy
RUN mvn clean package -DskipTests

# Fase 2: Esecuzione
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamo solo il file .jar finale dalla Fase 1, ignorando tutto il resto del codice sorgente
COPY --from=build /app/target/calcio-0.0.1-SNAPSHOT.jar app.jar

# Informiamo Docker che l'app ascolta sulla porta 8080 (fondamentale per Beanstalk)
EXPOSE 8080

# Comando di avvio dell'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]
