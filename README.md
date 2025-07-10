☁️ App Meteo 🇮🇹
Un'applicazione web semplice e responsiva che recupera e visualizza le temperature medie giornaliere dell'ultima settimana per diverse città italiane, utilizzando un backend Spring Boot e un frontend JavaScript con Chart.js.

✨ Caratteristiche
Backend RESTful: Sviluppato con Spring Boot per fornire dati meteo.

Integrazione API Esterna: Recupera dati meteorologici dall'API Open-Meteo.

Frontend Interattivo: Pagina web dinamica con HTML, CSS e JavaScript.

Visualizzazione Grafica: Mostra le temperature medie giornaliere in un grafico a barre intuitivo (Chart.js).

Selezione Città: Permette all'utente di scegliere una città italiana da un dropdown.

Containerizzazione Docker: Facile da costruire e deployare utilizzando Docker, garantendo portabilità.

🚀 Tecnologie Utilizzate
Backend
Java 17

Spring Boot

Maven

RestTemplate (per chiamate API)

Frontend
HTML5

CSS3

JavaScript (ES6+)

Chart.js

Deployment
Docker

Dockerfile (con build multi-stage per compilazione e runtime)





⚙️ Come Iniziare (Getting Started)
Per avviare l'applicazione, puoi scegliere tra l'esecuzione locale (se hai Java e Maven installati) o l'esecuzione tramite Docker (raccomandato per la portabilità).

Prerequisiti
Java 17 (per esecuzione locale)

Maven (per esecuzione locale)

Docker Desktop (per esecuzione con Docker)

1. Esecuzione Locale (senza Docker)
Clona il repository:

```
git clone https://github.com/alessioongi/weather-app.git
cd weather-app
```

Compila il progetto con Maven:

```
./mvnw.cmd clean install -DskipTests
```

(Su Linux/macOS, potresti usare ./mvnw clean install -DskipTests)

Esegui l'applicazione Spring Boot:

```
java -jar target/weather-app-0.0.1-SNAPSHOT.jar
```

Apri nel browser:
Naviga a 
```
http://localhost:8080
```

2. Esecuzione con Docker (Raccomandato)
Questo metodo costruisce l'applicazione Java all'interno del container Docker, quindi non è necessario avere Java o Maven installati localmente (solo Docker Desktop).

Clona il repository:

```
git clone https://github.com/alessioongi/weather-app.git
cd weather-app
```

Costruisci l'immagine Docker:
Questo comando leggerà il Dockerfile e compilerà l'applicazione Java al suo interno.

```
docker build -t weather-app .
```

Esegui il container Docker:

```
docker run -p 8080:8080 weather-app
```

Apri nel browser:
Naviga a 
```
http://localhost:8080
```
oppure avvialo da docker dekstop dove sono presenti i numeri delle porte Es 
```
8080:8080
```

si spera che funzioni !





🔌 Endpoint API
Il backend Spring Boot espone i seguenti endpoint REST:

GET /api/cities

Restituisce un array JSON di oggetti City (nome, latitudine, longitudine) di città italiane disponibili.

GET /api/weather?city={cityName}

Parametri: city (stringa, nome della città, es. "Roma").

Restituisce un oggetto JSON TemperatureData contenente labels (date), values (temperature medie in °C) e cityName.

🤝 Contribuire
Sentiti libero di aprire issue o inviare pull request per migliorare questo progetto.

📄 Licenza
Questo progetto è rilasciato sotto licenza MIT. Vedi il file LICENSE per maggiori dettagli.
