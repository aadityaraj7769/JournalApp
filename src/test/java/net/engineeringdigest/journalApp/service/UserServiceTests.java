package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @ParameterizedTest
  @ArgumentsSource(UserArgumentsProvider.class)
  public void testFindByUserName(User user) {
    assertTrue(userService.addNewUser(user));
  }
}
