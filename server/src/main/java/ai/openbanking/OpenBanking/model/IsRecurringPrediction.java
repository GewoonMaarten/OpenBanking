package ai.openbanking.OpenBanking.model;

import javax.persistence.*;

@Entity(name = "is_recurring_prediction")
public class IsRecurringPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;
    @Column(name = "0")
    private Double p0;
    @Column(name = "1")
    private Double p1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getP0() {
        return p0;
    }

    public void setP0(Double p0) {
        this.p0 = p0;
    }

    public Double getP1() {
        return p1;
    }

    public void setP1(Double p1) {
        this.p1 = p1;
    }
}
