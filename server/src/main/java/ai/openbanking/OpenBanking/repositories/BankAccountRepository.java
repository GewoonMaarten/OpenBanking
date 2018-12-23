package ai.openbanking.OpenBanking.repositories;

import ai.openbanking.OpenBanking.model.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
}
