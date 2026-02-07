package net.engineeringdigest.journalApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

  @Autowired
  private JournalEntryService journalEntryService;

  @Autowired
  private UserService userService;

  @GetMapping("/{userName}")
  public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
    User user = userService.findByUserName(userName);
    List<JournalEntry> allEntries = user.getJournalEntries();
    if(allEntries == null) {
      allEntries = new ArrayList<>();
    }
    return new ResponseEntity<>(allEntries, HttpStatus.OK);
  }

  @PostMapping("/{userName}")
  public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry, @PathVariable String userName) {
    try {
      journalEntryService.saveEntry(entry, userName);
      return new ResponseEntity<>(entry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

  }

  @GetMapping("id/{myId}")
  public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
    Optional<JournalEntry> entry = Optional.ofNullable(journalEntryService.getEntryById(myId));
    if (entry.isPresent()) {
      return new ResponseEntity<>(entry.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("id/{userName}/{myId}")
  public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
    journalEntryService.deleteEntryById(myId, userName);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{userName}/{myId}")
  public ResponseEntity<?> updateJournalEntryById(
      @PathVariable ObjectId myId,
      @RequestBody JournalEntry newEntry,
      @PathVariable String userName) {
    JournalEntry jEntry = journalEntryService.getEntryById(myId);
    if (jEntry != null) {
      jEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : jEntry.getTitle());
      jEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : jEntry.getContent());
      journalEntryService.saveEntry(jEntry);
      return new ResponseEntity<>(jEntry, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
