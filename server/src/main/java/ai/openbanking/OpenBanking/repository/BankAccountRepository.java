package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.BankAccount;
import ai.openbanking.OpenBanking.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends PagingAndSortingRepository<BankAccount, Integer> {

    Optional<List<BankAccount>> findByUser(User user);
}
