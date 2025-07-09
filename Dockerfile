# Usa un'immagine base OpenJDK per un ambiente di runtime Java leggero
FROM openjdk:17-jdk-slim

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il file JAR dell'applicazione nel container
# Assicurati che il nome del file JAR corrisponda a quello generato dal tuo build (es. target/weather-app-0.0.1-SNAPSHOT.jar)
# Se stai usando Maven, il nome predefinito sarà artifactId-version.jar
# Quindi, se il tuo pom.xml ha <artifactId>weather-app</artifactId> e <version>0.0.1-SNAPSHOT</version>
# il file sarà weather-app-0.0.1-SNAPSHOT.jar
COPY target/weather-app-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta su cui l'applicazione Spring Boot sarà in ascolto
EXPOSE 8080

# Comando per eseguire l'applicazione quando il container viene avviato
CMD ["java", "-jar", "app.jar"]