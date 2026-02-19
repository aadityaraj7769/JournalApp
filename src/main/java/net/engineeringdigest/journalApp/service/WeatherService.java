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

  @Autowired
  private RedisService radisService;

  public WeatherResponse getWeather(String city) {
    WeatherResponse weatherResponse = radisService.get("weather_of_" + city, WeatherResponse.class);
    if (weatherResponse != null) {
      return weatherResponse;
    } else {
      String url = appCache.APP_CACHE.get("weather_api").replace("<api_key>", apiKey).replace("<city>", city);
      ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
      WeatherResponse body = response.getBody();

      if(body != null) {
        radisService.set("weather_of_" + city, body, 300L);
      }
      return body;
    }

  }
}
