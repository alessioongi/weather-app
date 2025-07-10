Documentazione Progetto: App Meteo
Questo documento fornisce una panoramica completa del progetto "App Meteo", che recupera e visualizza le temperature medie giornaliere per diverse città italiane.

1. Panoramica del Progetto
L'App Meteo è un'applicazione web semplice che consente agli utenti di visualizzare le temperature medie giornaliere dell'ultima settimana per una città selezionata. Il backend è sviluppato con Spring Boot (Java) e si interfaccia con l'API Open-Meteo per recuperare i dati meteorologici. Il frontend è una semplice pagina HTML con JavaScript per la logica e Chart.js per la visualizzazione dei dati in un grafico a barre.

Caratteristiche principali:
Backend RESTful con Spring Boot.

Integrazione con l'API esterna Open-Meteo.

Frontend basato su HTML, CSS e JavaScript.

Visualizzazione dei dati tramite grafico Chart.js.

Selezione della città tramite dropdown.

Supporto Docker per un deployment facile e portatile.

2. Tecnologie Utilizzate
Backend:
Java 17: Linguaggio di programmazione.

Spring Boot: Framework per lo sviluppo rapido di applicazioni Java.

Maven: Strumento di gestione del progetto e di build.

RestTemplate: Client HTTP di Spring per chiamate API REST.

Jackson (integrato in Spring Boot): Libreria per la serializzazione/deserializzazione JSON.

Frontend:
HTML5: Struttura della pagina web.

CSS3: Stile e layout responsivo.

JavaScript (ES6+): Logica interattiva della pagina.

Chart.js: Libreria JavaScript per la creazione di grafici.

Deployment/Containerizzazione:
Docker: Piattaforma per containerizzare l'applicazione.

Dockerfile: File per definire l'immagine Docker.

(Opzionale) Docker Compose: Strumento per definire ed eseguire applicazioni Docker multi-container (non utilizzato nella versione attuale del Dockerfile, ma utile per futuri sviluppi).

3. Struttura del Progetto
Il progetto segue una struttura standard Maven e Spring Boot:

weatherapp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── weatherapp/
│   │   │               ├── WeatherApplication.java       # Classe principale Spring Boot
│   │   │               ├── controller/
│   │   │               │   └── WeatherController.java    # Controller REST
│   │   │               ├── model/
│   │   │               │   ├── City.java                 # Modello per la città
│   │   │               │   ├── DailyData.java            # Modello dati giornalieri API
│   │   │               │   ├── OpenMeteoResponse.java    # Modello risposta API
│   │   │               │   └── TemperatureData.java      # Modello dati per frontend
│   │   │               └── service/
│   │   │                   └── OpenMeteoService.java     # Servizio per logica API
│   │   └── resources/
│   │       ├── static/                                   # File statici del frontend
│   │       │   ├── index.html
│   │       │   ├── style.css
│   │       │   └── script.js
│   │       └── application.properties                  # Proprietà di configurazione Spring Boot
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── weatherapp/
│                       └── ... (test unitari)
├── pom.xml                                           # File di configurazione Maven
├── Dockerfile                                        # Definizione dell'immagine Docker
├── docker-compose.yml (opzionale)                    # Configurazione Docker Compose
└── .gitignore                                        # File e cartelle da ignorare per Git

4. Come Eseguire l'Applicazione
Puoi eseguire l'applicazione in due modi: direttamente sul tuo computer o tramite Docker.

4.1 Esecuzione Locale (senza Docker)
Assicurati di avere Java 17 e Maven installati.

Naviga nella directory radice del progetto:

cd /percorso/alla/tua/cartella/weatherapp

Compila il progetto:

./mvnw.cmd clean install -DskipTests

(Su Linux/macOS, potresti usare ./mvnw clean install -DskipTests)

Esegui l'applicazione:

java -jar target/weather-app-0.0.1-SNAPSHOT.jar

Accedi all'applicazione:
Apri il tuo browser e vai a http://localhost:8080.

4.2 Esecuzione con Docker
Assicurati di avere Docker Desktop installato e in esecuzione. Questo metodo è raccomandato per la portabilità.

Naviga nella directory radice del progetto:

cd /percorso/alla/tua/cartella/weatherapp

Costruisci l'immagine Docker:
Questo comando leggerà il Dockerfile e compilerà l'applicazione Java all'interno del container di build, creando il JAR e poi l'immagine finale.

docker build -t weather-app .

Esegui il container Docker:

docker run -p 8080:8080 weather-app

Accedi all'applicazione:
Apri il tuo browser e vai a http://localhost:8080.

5. Endpoint API (Backend)
Il backend Spring Boot espone i seguenti endpoint:

GET /api/cities:

Descrizione: Restituisce un elenco di tutte le città italiane disponibili per la selezione.

Risposta: List<City> (JSON array di oggetti City con name, latitude, longitude).

GET /api/weather?city={cityName}:

Descrizione: Recupera i dati di temperatura media giornaliera per l'ultima settimana per la città specificata.

Parametri di query:

city (String, obbligatorio): Il nome della città (es. "Roma", "Milano").

Risposta: TemperatureData (JSON oggetto con labels (date), values (temperature), cityName).

6. Frontend
Il frontend è una Single Page Application (SPA) che interagisce con il backend.

index.html: La struttura principale della pagina. Include un dropdown per la selezione della città e un'area per il grafico.

style.css: Definisce lo stile visivo dell'applicazione, inclusa la responsività.

script.js:

Al caricamento della pagina, effettua una chiamata a /api/cities per popolare il dropdown delle città.

Quando l'utente seleziona una città e clicca sul pulsante "Mostra Grafico Meteo", effettua una chiamata a /api/weather?city={selectedCityName}.

Utilizza Chart.js per disegnare un grafico a barre delle temperature medie giornaliere.

Gestisce lo stato di caricamento e visualizza eventuali messaggi di errore.

7. Integrazione con GitHub
Il progetto è progettato per essere gestito con Git e ospitato su GitHub.

Il file .gitignore è configurato per escludere i file generati (come la directory target/ e i file .jar) dal controllo di versione. Questo assicura che il repository contenga solo il codice sorgente e i file di configurazione essenziali.

Chiunque cloni il repository può utilizzare il Dockerfile per costruire l'applicazione da zero, senza dover pre-compilare il codice Java localmente.
