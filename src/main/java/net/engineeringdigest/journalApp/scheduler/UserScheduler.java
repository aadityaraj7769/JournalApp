package net.engineeringdigest.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class UserScheduler {

  @Autowired
  private EmailService emailService;

  @Autowired
  private UserRepositoryImpl userRepository;

  @Autowired
  private SentimentAnalysisService sentimentAnalysisService;

  @Autowired
  private AppCache appCache;

//  @Scheduled(cron = "0 0 9 * * SUN")
  public void fetchUsersAndSendSaMail() {
    List<User> users = userRepository.getUserForSA();

    for (User user : users) {
      List<JournalEntry> journalEntries = user.getJournalEntries();
      List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getSentiment()).collect(Collectors.toList());
      Map<Sentiment, Integer> sentimentCount = new HashMap<>();
      for (Sentiment sentiment : sentiments) {
        sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
      }

      Sentiment mostSentimentCounts = null;
      int maxCount = 0;
      for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
        if (entry.getValue() > maxCount) {
          mostSentimentCounts = entry.getKey();
          maxCount = entry.getValue();
        }
       }

      if (mostSentimentCounts != null) {
        emailService.sendMail(user.getEmail(), "Your Sentiment Analysis Result", mostSentimentCounts.toString());
      }
      }

    }

  @Scheduled(cron = "0 0/10 * ? * *")
  public void clearAppCache() {
    appCache.init();
  }
}
