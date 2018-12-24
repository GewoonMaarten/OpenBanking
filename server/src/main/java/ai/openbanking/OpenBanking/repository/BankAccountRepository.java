package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.BankAccount;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankAccountRepository extends PagingAndSortingRepository<BankAccount, Integer> {
}
