package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
