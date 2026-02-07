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
@RequestMapping("/user")
public class UserControllerV2 {
  @Autowired
  private UserService userService;

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAll();
  }

  @PostMapping
  public void createUser(@RequestBody User user) {
    userService.saveEntry(user);
  }

  @PutMapping("/{userName}")
  public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
    User userInDb = userService.findByUserName(userName);
    if (userInDb != null) {
      userInDb.setUserName(user.getUserName());
      userInDb.setPassword(user.getPassword());
      userService.saveEntry(userInDb);
    }
    return new ResponseEntity<>(userInDb, HttpStatus.NO_CONTENT);
  }
}
