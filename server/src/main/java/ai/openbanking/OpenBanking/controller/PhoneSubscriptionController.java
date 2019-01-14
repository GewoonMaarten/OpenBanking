package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.model.PhoneSubscription;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.PhoneSubscriptionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phone")
public class PhoneSubscriptionController {

    private final PhoneSubscriptionRepository repository;

    PhoneSubscriptionController(PhoneSubscriptionRepository repository) { this.repository = repository; }

    @GetMapping("/byTransaction")
    List<PhoneSubscription> findByTransaction(Transaction transaction) {
        String regex = transaction.getName().replaceAll("\\s", "|");
        return repository.findByProviderRegexAndPrice(regex, transaction.getAmount());
    }
}
