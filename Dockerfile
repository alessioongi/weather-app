# --- Stage 1: Build dell'applicazione Java ---
# Usa un'immagine Maven con JDK 17 come base per la fase di build.
# Questa immagine contiene Maven e un JDK completo, necessari per compilare il progetto.
FROM maven:3.8.7-openjdk-17 AS build

# Imposta la directory di lavoro all'interno di questo stage del container.
WORKDIR /app

# Copia i file di configurazione di Maven (pom.xml) per permettere a Maven di scaricare le dipendenze.
# Questo passaggio è ottimizzato per il caching di Docker: se il pom.xml non cambia,
# Docker non scaricherà di nuovo le dipendenze.
COPY pom.xml .

# Scarica le dipendenze del progetto.
# Le dipendenze verranno scaricate e messe in cache. Se il pom.xml non cambia,
# questo passaggio verrà saltato nelle build successive, velocizzando il processo.
RUN mvn dependency:go-offline

# Copia il codice sorgente dell'applicazione nella directory di lavoro.
COPY src ./src

# Compila l'applicazione Spring Boot e crea il file JAR eseguibile.
# `-DskipTests` è incluso qui per saltare l'esecuzione dei test durante la build Docker,
# come discusso in precedenza. Se i tuoi test sono stabili, puoi rimuoverlo.
RUN mvn clean install -DskipTests

# --- Stage 2: Runtime dell'applicazione ---
# Usa un'immagine OpenJDK più leggera (solo JRE) per l'esecuzione dell'applicazione.
# Questo riduce la dimensione finale dell'immagine del container.
FROM openjdk:17-jre-slim

# Imposta la directory di lavoro nel container di runtime.
WORKDIR /app

# Copia il file JAR compilato dallo stage di build (chiamato 'build') al container di runtime.
# Assicurati che il nome del JAR qui corrisponda a quello generato dal tuo pom.xml.
# Per default, è artifactId-version.jar (es. weather-app-0.0.1-SNAPSHOT.jar).
COPY --from=build /app/weather-app-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta su cui l'applicazione Spring Boot sarà in ascolto.
EXPOSE 8080

# Comando per eseguire l'applicazione quando il container viene avviato.
CMD ["java", "-jar", "app.jar"]
