package net.engineeringdigest.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

  private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

  @GetMapping
  public List<JournalEntry> getAll() {
    return new ArrayList<>(journalEntries.values());
  }

  @PostMapping
  public boolean createEntry(@RequestBody JournalEntry entry) {
    journalEntries.put(entry.getId(), entry);
    return true;
  }

  @GetMapping("id/{myId}")
  public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
    return journalEntries.get(myId);
  }

  @DeleteMapping("id/{myId}")
  public JournalEntry deleteJournalEntryById(@PathVariable ObjectId myId) {
    return journalEntries.remove(myId);
  }

  @PutMapping("id/{myId}")
  public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry entry) {
      return journalEntries.put(myId, entry);
  }
}
