package ai.openbanking.OpenBanking.controller;


import ai.openbanking.OpenBanking.NumberCalculator;
import ai.openbanking.OpenBanking.exception.BankAccountNotFoundException;
import ai.openbanking.OpenBanking.model.BankAccount;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.BankAccountRepository;
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

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final NumberCalculator calculator;

    public CalculatorController(
            TransactionRepository transactionRepository,
            BankAccountRepository bankAccountRepository,
            NumberCalculator calculator
    ) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.calculator = calculator;
    }

    @GetMapping("/expectedExpenses")
    public double expectedExpenses(@RequestParam("id") Integer bankAccountId,
                            @RequestParam("date") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate date) {

        List<Transaction> transactionList = this.transactionRepository
                .findByBankAccount_Id(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        return calculator.expectedExpenses(transactionList, date);
    }

    @GetMapping("/expectedBalance")
    public double expectedBalance(@RequestParam("id") Integer bankAccountId,
                                  @RequestParam("date") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate date) {
        BankAccount bankAccount = this.bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        List<Transaction> transactionList = this.transactionRepository
                .findByBankAccount_Id(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        return calculator.expectedBalance(transactionList, date, bankAccount.getBalance());
    }

    @GetMapping("/currentBalance")
    public double currentBalance(@RequestParam("id") Integer bankAccountId,
                                  @RequestParam("date") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate date) {

        List<Transaction> transactionList = this.transactionRepository
                .findByBankAccount_Id(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        return calculator.currentExpenses(transactionList, date);
    }
}
