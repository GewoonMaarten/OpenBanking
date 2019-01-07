package ai.openbanking.OpenBanking.controller;


import ai.openbanking.OpenBanking.NumberCalculator;
import ai.openbanking.OpenBanking.exception.BankAccountNotFoundException;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.TransactionRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calculate")
public class CalculatorController {

    private final TransactionRepository repository;
    private final NumberCalculator calculator;

    public CalculatorController(TransactionRepository repository, NumberCalculator calculator) {
        this.repository = repository;
        this.calculator = calculator;
    }

    @GetMapping("/expectedExpenses")
    public double expectedExpenses(@RequestParam("id") Integer bankAccountId,
                            @RequestParam("date") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate date) {

        List<Transaction> transactionList = this.repository
                .findByBankAccount_Id(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        return calculator.expectedExpenses(transactionList, date);
    }
}
