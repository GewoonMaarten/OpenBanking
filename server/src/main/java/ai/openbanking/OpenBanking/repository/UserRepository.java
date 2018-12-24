package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
}
