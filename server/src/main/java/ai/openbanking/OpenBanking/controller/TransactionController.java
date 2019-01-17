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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            @RequestParam Integer bankAccountId,
            @RequestParam Optional<Category> category,
            @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Optional<LocalDate> date) {

        return category.map(category1 -> repository.findByBankAccount_IdAndCategory(pageable, bankAccountId, category1)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId)))
                    .orElseGet(() -> date.map(localDate -> repository.findByBankAccount_IdAndDateBefore(pageable, bankAccountId, localDate)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId)))
                        .orElseGet(() -> repository.findByBankAccount_Id(pageable, bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId))));
    }

    @GetMapping("{id}")
    Transaction one(@PathVariable Integer id){
        return repository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @GetMapping("/outliers")
    Page<Transaction> outliers(Pageable pageable,
                               @RequestParam double threshold,
                               @RequestParam int bankAccountId,
                               @RequestParam Optional<Boolean> isRecurring,
                               @RequestParam Optional<Category> category,
                               @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Optional<LocalDate> dateStart,
                               @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") Optional<LocalDate> dateEnd) {

        final long currentTotal = pageable.getOffset() + pageable.getPageSize();
        Iterable<Transaction> transactionIterable;

        if(category.isPresent() && dateStart.isPresent() && dateEnd.isPresent()) {

            transactionIterable = repository
                    .findByBankAccount_IdAndCategoryAndDateBetween(
                        bankAccountId,
                        category.get(),
                        dateStart.get(),
                        dateEnd.get())
                    .orElseThrow(() -> new TransactionNotFoundException(category.get().getLabel()));

        } else if(category.isPresent() && !dateStart.isPresent() && !dateEnd.isPresent()) {

            transactionIterable = repository.findByBankAccount_IdAndCategory(bankAccountId, category.get())
                    .orElseThrow(() -> new TransactionNotFoundException(category.get().getLabel()));

        } else if (category.isPresent() && isRecurring.isPresent() && dateStart.isPresent() && dateEnd.isPresent()) {
            transactionIterable = repository
                    .findByBankAccount_IdAndCategoryAndIsRecurringAndDateBetween(
                    bankAccountId,
                    category.get(),
                    isRecurring.get(),
                    dateStart.get(),
                    dateEnd.get())
                    .orElseThrow(() -> new TransactionNotFoundException(category.get().getLabel()));
        } else {
            transactionIterable = repository.findByBankAccount_Id(bankAccountId)
                    .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));
        }

        List<Transaction> transactionList = new ArrayList<>();
        transactionIterable.forEach(transactionList::add);

        transactionList = calculator.zscore(transactionList).entrySet().stream().filter(x -> {
            Double zScore = x.getKey();
            double zScoreAbs = Math.abs(zScore);
            if (zScore > 0) return false;
            return zScoreAbs > threshold;
        }).map(Map.Entry::getValue).collect(Collectors.toList());

        return new PageImpl<>(transactionList, pageable, currentTotal);
    }
}
