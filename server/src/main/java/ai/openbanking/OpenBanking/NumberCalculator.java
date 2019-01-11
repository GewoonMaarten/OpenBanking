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

//        OptionalDouble avgAmount = transactionList
//                .stream()
//                .filter(transaction -> {
//                    LocalDate date = transaction.getDate();
//
//                    int previousMonth = today.getMonthValue() - 1;
//
//                    if (previousMonth == 0) {
//                        previousMonth = 12;
//                    }
//
//                    boolean t = (date.getMonthValue() == previousMonth ||
//                            date.getMonthValue() == today.getMonthValue()) &&
//                            date.getDayOfMonth() > today.getDayOfMonth() &&
//                            (date.getYear() == today.getYear() ||
//                                    date.getYear() == today.getYear() - 1) &&
//                            transaction.getAmount() < 0;
////
////                    System.out.println("\n");
////                    System.out.println(transaction.getName());
////                    System.out.println(transaction.getAmount());
////                    System.out.println(transaction.getDate());
////                    System.out.println("Is included: " + t);
////
//
//
//                    if(t) {
//                        System.out.println("\nTransaction: ");
//                        System.out.println(transaction.getName());
//                        System.out.println(transaction.getAmount());
//                        System.out.println(transaction.getDate());
//                    }
//
//                    return ((date.getMonthValue() == previousMonth ||
//                                    date.getMonthValue() == today.getMonthValue()) &&
//                            (date.getYear() == today.getYear() ||
//                                    date.getYear() == today.getYear() - 1)) &&
//                            date.getDayOfMonth() > today.getDayOfMonth() &&
//                            transaction.getAmount() < 0;
//                })
//                .collect(
//                        Collectors.groupingBy(
//                                (Transaction transaction) -> transaction.getDate().getMonth(),
//                                Collectors.summingDouble(Transaction::getAmount)
//                        )
//                )
//                .entrySet()
//                .stream()
//                .mapToDouble(Map.Entry::getValue)
//                .average();


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


    public double expectedBalance(List<Transaction> transactions, LocalDate today, Double balance){
        double expenses = expectedExpenses(transactions, today);
        return balance + expenses;
    }

    /**
     * Get the outliers based on the calculated z-score and the given threshold.
     * @param threshold threshold for the z-score
     * @param transactions list of transactions
     */
    public List<Transaction> elevatedTransactions(List<Transaction> transactions, double threshold){

        double average = transactions
                .stream().mapToDouble(Transaction::getAmount).average().orElse(0.0d);

        double std = transactions
                .stream().map(Transaction::getAmount).collect(DoubleStatistics.collector()).getStandardDeviation();

        return transactions.stream().filter(transaction -> {
            //double zScore = Math.abs((transaction.getAmount() - average) / std);
            double zScore = (transaction.getAmount() - average) / std;

            if (zScore > threshold) {
                System.out.println("Outlier: ");
                System.out.println(transaction.getName());
                System.out.println(transaction.getAmount());
                System.out.println(zScore);
            }

            return zScore > threshold;
        }).collect(Collectors.toList());
    }
}
