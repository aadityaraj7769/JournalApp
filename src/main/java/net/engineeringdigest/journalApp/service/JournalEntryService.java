package net.engineeringdigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Slf4j
public class JournalEntryService {

  @Autowired
  private JournalEntryRepository journalEntryRepository;

  @Autowired
  private UserService userService;

  @Transactional
  public void saveEntry(JournalEntry entry, String userName) {
    try {
      User user = userService.findByUserName(userName);
      entry.setDate(LocalDateTime.now());
      JournalEntry saved = journalEntryRepository.save(entry);
      user.getJournalEntries().add(saved);
//      user.setUserName(null);
      userService.saveEntry(user);
    } catch (Exception e) {
      log.error("Error saving journal entry: " + e.getMessage());
      throw new RuntimeException("An error occured while saving the entry. ", e);
    }
  }

  public void saveEntry(JournalEntry entry) {
    try {
      journalEntryRepository.save(entry);
    } catch (Exception e) {
      log.error("Error saving journal entry: " + e.getMessage());
    }
  }

  public List<JournalEntry> getAllEntries() {
    return journalEntryRepository.findAll();
  }

  public JournalEntry getEntryById(ObjectId id) {
    return journalEntryRepository.findById(id).orElse(null);
  }

  @Transactional
  public void deleteEntryById(ObjectId id, String userName) {
    try {
      User user = userService.findByUserName(userName);
      boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
      if(removed) {
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
      }
    } catch (Exception e) {
      log.error("Error deleting journal entry: " + e.getMessage());
    }

  }


}
