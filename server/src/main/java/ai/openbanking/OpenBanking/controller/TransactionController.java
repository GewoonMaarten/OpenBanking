package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.exception.BankAccountNotFoundException;
import ai.openbanking.OpenBanking.exception.TransactionNotFoundException;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository repository;

    TransactionController(TransactionRepository repository){ this.repository = repository; }

    @GetMapping("")
    Page<Transaction> all(Pageable pageable){
        return repository.findAll(pageable);
    }

    @GetMapping("{id}")
    Transaction one(@PathVariable Integer id){
        return repository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @GetMapping("/bankAccount")
    Page<Transaction> getByBankAccount_Id(Pageable pageable, @RequestParam Integer id) {
        return repository.findByBankAccount_Id(pageable, id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));
    }

}
