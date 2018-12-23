package ai.openbanking.OpenBanking.repositories;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
