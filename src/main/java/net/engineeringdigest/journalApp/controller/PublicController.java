package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @GetMapping("/health-check")
  public String healthCheck() {
    log.info("Health check endpoint hit");
    return "OK";
  }

  @PostMapping("/signup")
  public void signup(@RequestBody User user) {
    userService.addNewUser(user);
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody User user) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
      UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
      String token = jwtUtil.generateToken(userDetails.getUsername());
      return new ResponseEntity<>(token, HttpStatus.OK);

    } catch (Exception e) {
      log.error("Error during authentication: " + e.getMessage());
      return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
    }
  }

}
