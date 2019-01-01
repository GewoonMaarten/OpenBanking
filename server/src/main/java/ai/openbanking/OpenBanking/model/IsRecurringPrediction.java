package ai.openbanking.OpenBanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "is_recurring_predictions")
public class IsRecurringPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;
    @Column(name = "0")
    private Double p0;
    @Column(name = "1")
    private Double p1;

    @ManyToOne()
    @JoinColumn(name = "transaction")
    private Transaction transaction;
}
