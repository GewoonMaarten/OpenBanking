package ai.openbanking.OpenBanking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstname;
    private String lastname;
    private String insertion;

    private String email;
    private String password;

    private Date birthdate;

    public User(String firstname, String lastname, String insertion, String email, String password, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.insertion = insertion;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public User(String firstname, String lastname, String email, String password, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
