package ai.openbanking.OpenBanking.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "phone_subscription")
public class PhoneSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String provider;
    private Integer sms;
    private Integer minutes;
    @Column(name = "internet_gb")
    private Double internetGB;
    private Double price;
    private boolean discount;
    @Column(name = "connection_costs")
    private Integer connectionCost;
    @Column(name = "renewable_after")
    private Integer renewableAfter;
    private Integer duration;
    private String description;
}
