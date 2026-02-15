package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherService {
  private static final String apiKey = "4f7b6ce32077567295778b0902c9beec";

  private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

  @Autowired
  private RestTemplate restTemplate;

  public WeatherResponse getWeather(String city) {
    String url = API.replace("API_KEY", apiKey).replace("CITY", city);
    ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
    return response.getBody();
  }
}
