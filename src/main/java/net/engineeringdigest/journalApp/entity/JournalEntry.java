package net.engineeringdigest.journalApp.entity;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "journalEntries")
@Getter
@Setter
public class JournalEntry {

  @Id
  private ObjectId id;
  @NonNull
  private String title;
  private String content;
  private LocalDateTime date;

}
