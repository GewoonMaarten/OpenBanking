package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
