package ai.openbanking.OpenBanking.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
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

}
