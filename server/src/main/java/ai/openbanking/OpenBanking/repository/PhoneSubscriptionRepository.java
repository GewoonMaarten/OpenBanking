package ai.openbanking.OpenBanking.repository;

import ai.openbanking.OpenBanking.model.PhoneSubscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneSubscriptionRepository extends PagingAndSortingRepository<PhoneSubscription, Integer> {

    @Query(
            nativeQuery=true,
            value = "SELECT * FROM phone_subscription p WHERE p.provider REGEXP :regex AND p.price = :price"
    )
    List<PhoneSubscription> findByProviderRegexAndPrice(@Param("regex") String regex, @Param("price") Double price);
    Page<PhoneSubscription> findByPriceOrderByInternetGBDesc(Double price, Pageable pageable);
    Page<PhoneSubscription> findByPriceOrderByMinutesDesc(Double price, Pageable pageable);
    Page<PhoneSubscription> findByPriceOrderBySmsDesc(Double price, Pageable pageable);
}
