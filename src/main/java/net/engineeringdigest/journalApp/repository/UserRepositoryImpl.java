package net.engineeringdigest.journalApp.repository;

import java.util.List;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


@Component
public class UserRepositoryImpl {

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<User> getUserForSA() {
    Query query = new Query();

    Criteria criteria = new Criteria();

    query.addCriteria(criteria.andOperator(
        Criteria.where("email").exists(true),
        Criteria.where("email").ne(null).ne(""),
        Criteria.where("sentimentAnalysis").is("true"))
       );

    List<User> users = mongoTemplate.find(query, User.class);
    return users;
  }
}
