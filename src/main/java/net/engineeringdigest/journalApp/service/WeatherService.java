package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {
  @Value("${weather.api.key}")
  private String apiKey;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AppCache appCache;

  public WeatherResponse getWeather(String city) {
    String url = appCache.APP_CACHE.get("weather_api").replace("<api_key>", apiKey).replace("<city>", city);
    ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
    return response.getBody();
  }
}
