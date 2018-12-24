package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {
}
