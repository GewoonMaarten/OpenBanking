package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.NumberCalculator;
import ai.openbanking.OpenBanking.exception.PhoneSubscriptionNotFoundException;
import ai.openbanking.OpenBanking.model.Category;
import ai.openbanking.OpenBanking.model.PhoneSubscription;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.PhoneSubscriptionRepository;
import ai.openbanking.OpenBanking.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phone")
public class PhoneSubscriptionController {

    private final PhoneSubscriptionRepository phoneSubscriptionRepository;
    private final TransactionRepository transactionRepository;
    private final NumberCalculator calculator;

    PhoneSubscriptionController(
            PhoneSubscriptionRepository phoneSubscriptionRepository,
            TransactionRepository transactionRepository,
            NumberCalculator calculator
    ) {
        this.phoneSubscriptionRepository = phoneSubscriptionRepository;
        this.transactionRepository = transactionRepository;
        this.calculator = calculator;
    }

    @GetMapping("/byBankAccount")
    List<PhoneSubscription> findByBankAccount(@Param("bankAccountId") Integer bankAccountId) {

        Optional<Iterable<Transaction>> transactionIterable = transactionRepository
                .findByBankAccount_IdAndCategory(bankAccountId, Category.telecom);

        if (transactionIterable.isPresent()) {
            List<Transaction> transactionList = new ArrayList<>();
            transactionIterable.get().forEach(transactionList::add);

            Transaction transaction = calculator.zscore(transactionList)
                    .entrySet()
                    .stream()
                    .filter(x -> x.getKey() >= 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    .entrySet().iterator().next().getValue();

            String regex = transaction.getName().replaceAll("\\s", "|");
            Double payment = Math.abs(transaction.getAmount());

            return phoneSubscriptionRepository.findByProviderRegexAndPrice(regex, payment);
        }

        return null;
    }

    @GetMapping("alternative")
    Page<PhoneSubscription> alternative(
            Pageable pageable,
            @Param("id") Integer id,
            @Param("internet") Optional<Boolean> internet,
            @Param("minutes") Optional<Boolean> minutes,
            @Param("sms") Optional<Boolean> sms
    ) {
        PhoneSubscription phoneSubscription = phoneSubscriptionRepository.findById(id)
                .orElseThrow(() -> new PhoneSubscriptionNotFoundException(id));

        if(internet.isPresent() && internet.get()) {
            return phoneSubscriptionRepository.findByPriceOrderByInternetGBDesc(phoneSubscription.getPrice(), pageable);
        } else if (minutes.isPresent() && minutes.get()) {
            return phoneSubscriptionRepository.findByPriceOrderByMinutesDesc(phoneSubscription.getPrice(), pageable);
        } else if (sms.isPresent() && sms.get()) {
            return phoneSubscriptionRepository.findByPriceOrderBySmsDesc(phoneSubscription.getPrice(), pageable);
        }

        return null;
    }
}
