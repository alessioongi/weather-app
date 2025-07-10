# Usa un'immagine OpenJDK con JDK 17 come base.
FROM openjdk:17-jdk-slim

# Imposta le variabili d'ambiente per Maven
ENV MAVEN_VERSION 3.9.6
ENV MAVEN_HOME /usr/share/maven
ENV PATH $MAVEN_HOME/bin:$PATH

# Scarica e installa Maven manualmente
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl unzip && \
    curl -fsSL https://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.zip -o /tmp/apache-maven.zip && \
    unzip /tmp/apache-maven.zip -d /usr/share && \
    mv /usr/share/apache-maven-$MAVEN_VERSION $MAVEN_HOME && \
    rm /tmp/apache-maven.zip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Imposta la directory di lavoro all'interno del container.
WORKDIR /app

# Copia i file di configurazione di Maven (pom.xml) per permettere a Maven di scaricare le dipendenze.
COPY pom.xml .

# Scarica le dipendenze del progetto.
RUN mvn dependency:go-offline

# Copia il codice sorgente dell'applicazione nella directory di lavoro.
COPY src ./src

# Compila l'applicazione Spring Boot e crea il file JAR eseguibile.
RUN mvn clean install -DskipTests

# Sposta il JAR compilato dalla sottocartella 'target' alla directory radice del progetto nel container.
RUN mv target/weather-app-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta su cui l'applicazione Spring Boot sarà in ascolto.
EXPOSE 8080

# Comando per eseguire l'applicazione quando il container viene avviato.
CMD ["java", "-jar", "app.jar"]