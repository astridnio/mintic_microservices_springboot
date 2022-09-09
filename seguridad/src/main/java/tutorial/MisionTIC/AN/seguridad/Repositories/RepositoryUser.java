package tutorial.MisionTIC.AN.seguridad.Repositories;

import tutorial.MisionTIC.AN.seguridad.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryUser extends MongoRepository<User, String> {

}
