package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.NumberCalculator;
import ai.openbanking.OpenBanking.exception.BankAccountNotFoundException;
import ai.openbanking.OpenBanking.exception.TransactionNotFoundException;
import ai.openbanking.OpenBanking.model.Category;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository repository;
    private final NumberCalculator calculator;

    TransactionController(TransactionRepository repository, NumberCalculator calculator){
        this.repository = repository;
        this.calculator = calculator;
    }

    @GetMapping("")
    Page<Transaction> all(
            Pageable pageable,
            @RequestParam Optional<Integer> bankAccountId,
            @RequestParam Optional<Category> category) {

        if (bankAccountId.isPresent()) {
            return repository.findByBankAccount_Id(pageable, bankAccountId.get())
                    .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId.get()));
        } else if (category.isPresent()) {
            return repository.findByCategory(pageable, category.get())
                    .orElseThrow(() -> new TransactionNotFoundException(category.get().getLabel()));
        }
        else {
            return repository.findAll(pageable);
        }
    }

    @GetMapping("{id}")
    Transaction one(@PathVariable Integer id){
        return repository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @GetMapping("/outliers")
    Page<Transaction> outliers(Pageable pageable, @RequestParam double threshold) {

        final long currentTotal = pageable.getOffset() + pageable.getPageSize();

        Iterable<Transaction> transactionIterable = repository.findAll();
        List<Transaction> transactionList = new ArrayList<>();
        transactionIterable.forEach(transactionList::add);

        transactionList = calculator.elevatedTransactions(transactionList, threshold);

        return new PageImpl<>(transactionList, pageable, currentTotal);
    }
}
