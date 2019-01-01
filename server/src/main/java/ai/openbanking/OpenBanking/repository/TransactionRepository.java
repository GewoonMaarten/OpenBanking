package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    Optional<Page<Transaction>> findByBankAccount_Id(Pageable pageable, Integer id);
}
