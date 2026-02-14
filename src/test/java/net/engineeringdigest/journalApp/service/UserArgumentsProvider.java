package net.engineeringdigest.journalApp.service;

import java.util.stream.Stream;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;


public class UserArgumentsProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    return Stream.of(
        Arguments.of(User.builder().userName("RajAditya").password("RajAditya").build()),
        Arguments.of(User.builder().userName("RajAnshu").password("RajAnshu").build())
    );
  }
}
