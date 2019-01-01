package ai.openbanking.OpenBanking;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NumberCalculator {


//    public Float balance(List<Transaction> transactionList, Date today) {
//    }

    public double expectedExpenses(List<Transaction> transactionList, Date today) {
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //Inefficient but it works
        OptionalDouble avgAmount = transactionList
                .stream()
                .filter(transaction -> {
                    LocalDate date = transaction.getDate();

                    return date.getMonthValue() == localDate.getMonthValue() &&
                            date.getDayOfMonth() > localDate.getDayOfMonth() &&
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
}
