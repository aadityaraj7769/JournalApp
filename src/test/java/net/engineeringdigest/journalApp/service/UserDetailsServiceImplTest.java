package net.engineeringdigest.journalApp.service;

import java.util.ArrayList;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;


public class UserDetailsServiceImplTest {

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loadUserByUsername() {
    when(userRepository.findByUserName(ArgumentMatchers.anyString())).
        thenReturn(User.builder().userName("RajAditya").password("ram").roles(new ArrayList<>()).build());
    UserDetails user = userDetailsService.loadUserByUsername("RajAditya");
    Assertions.assertNotNull(user);
  }

}
