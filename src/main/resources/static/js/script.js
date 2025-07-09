// src/main/resources/static/script.js
document.addEventListener('DOMContentLoaded', () => {
    const fetchWeatherBtn = document.getElementById('fetchWeatherBtn');
    const temperatureChartCanvas = document.getElementById('temperatureChart');
    const cityNameDisplay = document.getElementById('cityNameDisplay');
    const loadingMessage = document.getElementById('loadingMessage');
    const errorMessage = document.getElementById('errorMessage');
    const citySelect = document.getElementById('citySelect'); // Nuovo elemento dropdown

    let temperatureChart = null; // Variabile per mantenere l'istanza del grafico

    /**
     * Carica la lista delle città dal backend e popola il dropdown.
     */
    async function loadCities() {
        try {
            const response = await fetch('/api/cities');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const cities = await response.json();

            citySelect.innerHTML = ''; // Pulisci le opzioni esistenti

            if (cities && cities.length > 0) {
                cities.forEach(city => {
                    const option = document.createElement('option');
                    option.value = city.name; // Il valore sarà il nome della città
                    option.textContent = city.name;
                    citySelect.appendChild(option);
                });
                // Seleziona la prima città di default o una predefinita
                if (citySelect.options.length > 0) {
                    citySelect.value = cities[0].name; // Seleziona la prima città
                }
            } else {
                errorMessage.textContent = 'Nessuna città disponibile dal backend.';
                errorMessage.classList.remove('hidden');
            }
        } catch (error) {
            console.error('Errore nel caricamento delle città:', error);
            errorMessage.textContent = 'Impossibile caricare la lista delle città.';
            errorMessage.classList.remove('hidden');
        }
    }

    // Carica le città all'avvio della pagina
    loadCities();

    fetchWeatherBtn.addEventListener('click', async () => {
        // Nascondi messaggi precedenti e mostra caricamento
        errorMessage.classList.add('hidden');
        loadingMessage.classList.remove('hidden');
        cityNameDisplay.textContent = ''; // Pulisci il nome della città precedente

        if (temperatureChart) {
            temperatureChart.destroy(); // Distruggi il grafico esistente prima di crearne uno nuovo
        }

        const selectedCityName = citySelect.value; // Ottieni la città selezionata

        if (!selectedCityName) {
            errorMessage.textContent = 'Seleziona una città prima di continuare.';
            errorMessage.classList.remove('hidden');
            loadingMessage.classList.add('hidden');
            return;
        }

        try {
            // Chiama l'endpoint Spring Boot passando il nome della città come parametro
            const response = await fetch(`/api/weather?city=${encodeURIComponent(selectedCityName)}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();

            loadingMessage.classList.add('hidden'); // Nascondi il messaggio di caricamento

            if (data && data.labels && data.values) {
                cityNameDisplay.textContent = `Temperature per ${data.cityName}`;
                renderChart(data.labels, data.values);
            } else {
                errorMessage.textContent = 'Dati non validi ricevuti dall\'API.';
                errorMessage.classList.remove('hidden');
            }

        } catch (error) {
            console.error('Errore nel recupero dei dati meteo:', error);
            loadingMessage.classList.add('hidden');
            errorMessage.textContent = 'Impossibile recuperare i dati meteo. Riprova più tardi.';
            errorMessage.classList.remove('hidden');
        }
    });

    /**
     * Funzione per renderizzare il grafico a barre.
     * @param {Array<string>} labels - Etichette per l'asse X (date).
     * @param {Array<number>} values - Valori per l'asse Y (temperature).
     */
    function renderChart(labels, values) {
        const ctx = temperatureChartCanvas.getContext('2d');
        temperatureChart = new Chart(ctx, {
            type: 'bar', // Tipo di grafico: a barre
            data: {
                labels: labels, // Date
                datasets: [{
                    label: 'Temperatura Media Giornaliera (°C)',
                    data: values, // Temperature
                    backgroundColor: 'rgba(75, 192, 192, 0.8)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                    borderRadius: 5, // Angoli arrotondati per le barre
                    hoverBackgroundColor: 'rgba(75, 192, 192, 1)',
                    hoverBorderColor: 'rgba(75, 192, 192, 1)',
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true, // Permette al grafico di ridimensionarsi
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                        labels: {
                            font: {
                                size: 14
                            }
                        }
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.dataset.label + ': ' + context.parsed.y + '°C';
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: false, // Le temperature possono essere negative
                        title: {
                            display: true,
                            text: 'Temperatura (°C)',
                            font: {
                                size: 16
                            }
                        }
                    }
                }
            }
        });
    }
});