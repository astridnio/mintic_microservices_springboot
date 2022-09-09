package tutorial.MisionTIC.AN.seguridad.Repositories;

import tutorial.MisionTIC.AN.seguridad.Models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryRole extends MongoRepository<Role,String> {
}
