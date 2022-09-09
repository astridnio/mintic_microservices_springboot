package tutorial.MisionTIC.AN.seguridad.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorial.MisionTIC.AN.seguridad.Models.Claim;

public interface RepositoryClaim extends MongoRepository<Claim,String> {
}
