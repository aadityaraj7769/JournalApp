package net.engineeringdigest.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AppCache {

  public enum keys{
    WEATHER_API("weather_api");

    private final String key;

    keys(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }
  }

  @Autowired
  private ConfigJournalAppRepository configJournalAppRepository;

  public Map<String, String> APP_CACHE;

  @PostConstruct
  public void init() {
    APP_CACHE = new HashMap<>();
    List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
    for(ConfigJournalAppEntity entity : all) {
      APP_CACHE.put(entity.getKey(), entity.getValue());
    }
  }
}
