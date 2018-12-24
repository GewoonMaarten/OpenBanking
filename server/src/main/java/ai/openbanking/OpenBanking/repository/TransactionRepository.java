package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
