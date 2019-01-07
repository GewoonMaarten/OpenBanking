package ai.openbanking.OpenBanking;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NumberCalculator {


    public double balance(List<Transaction> transactionList, Date today) {
        return 0;
    }

    /**
     * Calculates the average expected expenses for the days left in the current month
     * based on the average of the sum of expenses from previous years
     * @param today Today's date
     * @param transactionList A list of transactions
     * @return double
     */
    public double expectedExpenses(List<Transaction> transactionList, LocalDate today) {

        //Inefficient but it works
        OptionalDouble avgAmount = transactionList
                .stream()
                .filter(transaction -> {
                    LocalDate date = transaction.getDate();

                    return date.getMonthValue() == today.getMonthValue() &&
                            date.getDayOfMonth() > today.getDayOfMonth() &&
                            transaction.getAmount() < 0;
                })
                .collect(
                    Collectors.groupingBy(
                        (Transaction transaction) -> transaction.getDate().getYear(),
                        Collectors.summingDouble(Transaction::getAmount)
                    )
                )
                .entrySet()
                .stream()
                .mapToDouble(Map.Entry::getValue)
                .average();

        return avgAmount.isPresent() ? avgAmount.getAsDouble() : 0;
    }

    public double expectedBalance(){
        return 0;
    }

    public void elevatedRecurringTransactions(){}

    public void elevatedTransactions(){}
}
