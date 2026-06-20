# Usiamo un'immagine base leggerissima con solo il runtime Java 17
FROM eclipse-temurin:17-jre-alpine

# Impostiamo la cartella di lavoro all'interno del container
WORKDIR /app

# Copiamo il file .jar generato da CodeBuild dentro il container
# (Il file buildspec.yml si occupa di rinominare il jar originale in 'app.jar')
COPY app.jar app.jar

# Esponiamo la porta 8080 (quella di default di Spring Boot)
# Elastic Beanstalk redirigerà il traffico web (porta 80) automaticamente alla 8080 del container
EXPOSE 8080

# Comando di avvio dell'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]
