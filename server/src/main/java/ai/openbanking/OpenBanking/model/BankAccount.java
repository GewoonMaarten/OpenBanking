package ai.openbanking.OpenBanking.model;

import javax.persistence.*;

@Entity(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String iban;
    @ManyToOne
    private User user;

    public BankAccount(String iban, User user) {
        this.iban = iban;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
