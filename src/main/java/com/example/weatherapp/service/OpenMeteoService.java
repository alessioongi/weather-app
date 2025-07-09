package com.example.weatherapp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.weatherapp.model.City;
import com.example.weatherapp.model.OpenMeteoResponse;
import com.example.weatherapp.model.TemperatureData;

@Service
public class OpenMeteoService {

    private final RestTemplate restTemplate;
    private final List<City> cities;
    // private final Random random; // Rimosso se non più necessario per getRandomCity

    public OpenMeteoService() {
        this.restTemplate = new RestTemplate();
        // Lista predefinita di città italiane
        this.cities = Arrays.asList(
                new City("Roma", 41.9028, 12.4964),
                new City("Milano", 45.4642, 9.1900),
                new City("Napoli", 40.8518, 14.2681),
                new City("Torino", 45.0703, 7.6869),
                new City("Firenze", 43.7696, 11.2558),
                new City("Venezia", 45.4408, 12.3155),
                new City("Bologna", 44.4949, 11.3426),
                new City("Genova", 44.4056, 8.9463),
                new City("Palermo", 38.1157, 13.3613),
                new City("Bari", 41.1171, 16.8719)
        );
        // this.random = new Random(); // Rimosso se non più necessario per getRandomCity
    }

    /**
     * Restituisce la lista di tutte le città disponibili.
     * @return Una lista di oggetti City.
     */
    public List<City> getAllCities() {
        return cities;
    }

    /**
     * Trova una città per nome.
     * @param cityName Il nome della città da cercare.
     * @return L'oggetto City corrispondente, o null se non trovata.
     */
    public City getCityByName(String cityName) {
        return cities.stream()
                .filter(city -> city.getName().equalsIgnoreCase(cityName))
                .findFirst()
                .orElse(null);
    }

    // Il metodo getRandomCity() può essere rimosso se non più utilizzato
    // public City getRandomCity() {
    //     return cities.get(random.nextInt(cities.size()));
    // }

    /**
     * Recupera i dati di temperatura media giornaliera per una data città
     * per le ultime 4 settimane.
     * @param city La città per cui recuperare i dati.
     * @return Un oggetto TemperatureData contenente etichette (date) e valori (temperature).
     */
    public TemperatureData getTemperatureDataForCity(City city) {
        if (city == null) {
            return new TemperatureData(List.of(), List.of(), "Nessuna città selezionata");
        }

        LocalDate endDate = LocalDate.now();
        // Recupera i dati per le ultime 4 settimane (28 giorni)
        LocalDate startDate = endDate.minusWeeks(4);

        String apiUrl = UriComponentsBuilder.fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", city.getLatitude())
                .queryParam("longitude", city.getLongitude())
                .queryParam("daily", "temperature_2m_mean")
                .queryParam("timezone", "Europe/Berlin") // Fuso orario comune per l'Italia
                .queryParam("start_date", startDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .queryParam("end_date", endDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .toUriString();

        try {
            OpenMeteoResponse response = restTemplate.getForObject(apiUrl, OpenMeteoResponse.class);

            if (response != null && response.getDaily() != null) {
                List<String> dates = response.getDaily().getTime();
                List<Double> temperatures = response.getDaily().getTemperature2mMean();

                // Filtra solo i dati per gli ultimi 7 giorni (ultima settimana) se ce ne sono di più
                // o tutti i dati disponibili se meno di 7
                int dataSize = dates.size();
                int startIndex = Math.max(0, dataSize - 7); // Prende gli ultimi 7 giorni

                List<String> lastWeekLabels = dates.subList(startIndex, dataSize);
                List<Double> lastWeekValues = temperatures.subList(startIndex, dataSize);

                return new TemperatureData(lastWeekLabels, lastWeekValues, city.getName());
            }
        } catch (Exception e) {
            System.err.println("Errore durante il recupero dei dati meteo per " + city.getName() + ": " + e.getMessage());
            // In caso di errore, restituisce dati vuoti o gestisce l'errore a seconda della logica desiderata
        }
        return new TemperatureData(List.of(), List.of(), city.getName());
    }
}