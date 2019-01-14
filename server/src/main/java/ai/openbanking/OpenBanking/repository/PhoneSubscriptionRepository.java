package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.PhoneSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneSubscriptionRepository extends JpaRepository<PhoneSubscription, Integer> {

    @Query(
            nativeQuery=true,
            value = "SELECT * FROM phone_subscription p WHERE p.provider REGEXP :regex AND p.price = :price"
    )
    List<PhoneSubscription> findByProviderRegexAndPrice(@Param("regex") String regex, @Param("price") Double price);
}
