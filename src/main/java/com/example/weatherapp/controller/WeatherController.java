package com.example.weatherapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherapp.model.City;
import com.example.weatherapp.model.TemperatureData;
import com.example.weatherapp.service.OpenMeteoService;

@RestController
@RequestMapping("/api")
// Permette richieste da qualsiasi origine (utile per lo sviluppo frontend)
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private final OpenMeteoService openMeteoService;

    public WeatherController(OpenMeteoService openMeteoService) {
        this.openMeteoService = openMeteoService;
    }

    /**
     * Endpoint per ottenere la lista di tutte le città disponibili.
     * @return Una lista di oggetti City.
     */
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return openMeteoService.getAllCities();
    }

    /**
     * Endpoint per ottenere i dati di temperatura per una città specifica.
     * Accetta il nome della città come parametro di richiesta.
     * @param cityName Il nome della città per cui recuperare i dati.
     * @return Un oggetto TemperatureData.
     */
    @GetMapping("/weather")
    public TemperatureData getWeather(@RequestParam(name = "city", required = false) String cityName) {
        City selectedCity;
        if (cityName != null && !cityName.isEmpty()) {
            selectedCity = openMeteoService.getCityByName(cityName);
        } else {
            // Se non viene specificata una città, seleziona la prima della lista come fallback
            // O potresti voler restituire un errore o una città predefinita.
            // Per semplicità, prendiamo la prima città se non specificata.
            selectedCity = openMeteoService.getAllCities().isEmpty() ? null : openMeteoService.getAllCities().get(0);
        }
        return openMeteoService.getTemperatureDataForCity(selectedCity);
    }
}
