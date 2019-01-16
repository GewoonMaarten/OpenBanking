package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.Category;
import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {



    Optional<Page<Transaction>> findByBankAccount_Id(Pageable pageable, Integer id);
    Optional<List<Transaction>> findByBankAccount_Id(Integer id);
    Optional<Page<Transaction>> findByBankAccount_IdAndCategory(Pageable pageable, Integer id, Category category);
    Optional<Iterable<Transaction>> findByBankAccount_IdAndCategory(Integer id, Category category);
    Optional<Iterable<Transaction>> findByBankAccount_IdAndCategoryAndDateBetween(
            Integer id,
            Category category,
            LocalDate dateStart,
            LocalDate dateEnd);
    Optional<Iterable<Transaction>> findByBankAccount_IdAndCategoryAndIsRecurringAndDateBetween(
            Integer id,
            Category category,
            Boolean isRecurring,
            LocalDate dateStart,
            LocalDate dateEnd);
}
