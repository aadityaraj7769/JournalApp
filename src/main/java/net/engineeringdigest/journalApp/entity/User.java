package net.engineeringdigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private ObjectId id;

  @Indexed(unique = true)
  @NonNull
  private String userName;
  private String email;
  private boolean sentimentAnalysis;
  @NonNull
  private String password;
  @DBRef
  private List<JournalEntry> journalEntries = new ArrayList<>();

  private List<String> roles;

}
