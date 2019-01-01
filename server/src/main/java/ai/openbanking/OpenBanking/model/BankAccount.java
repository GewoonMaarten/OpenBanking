package ai.openbanking.OpenBanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String iban;

    @ManyToOne()
    @JoinColumn(name = "user", nullable = false)
    private User user;

}
