package ai.openbanking.OpenBanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "category_predictions")
public class CategoryPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String label;

    private Double boodschappen;
    private Double consumptie;
    private Double educatie;
    private Double huishouden;
    private Double inkomsten;
    private Double kleding;
    @Column(name = "medische_kosten")
    private Double medischeKosten;
    @Column(name = "overige_uitgaven")
    private Double overigeUitgaven;
    private Double telecom;
    private Double vervoer;
    private Double verzekeringen;
    @Column(name = "vrije_tijd")
    private Double vrijeTijd;

    @ManyToOne()
    @JoinColumn(name = "transaction")
    private Transaction transaction;


}
