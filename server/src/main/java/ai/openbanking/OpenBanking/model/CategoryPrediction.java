package ai.openbanking.OpenBanking.model;

import javax.persistence.*;

@Entity(name = "category_prediction")
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

    public Double getBoodschappen() {
        return boodschappen;
    }

    public void setBoodschappen(Double boodschappen) {
        this.boodschappen = boodschappen;
    }

    public Double getConsumptie() {
        return consumptie;
    }

    public void setConsumptie(Double consumptie) {
        this.consumptie = consumptie;
    }

    public Double getEducatie() {
        return educatie;
    }

    public void setEducatie(Double educatie) {
        this.educatie = educatie;
    }

    public Double getHuishouden() {
        return huishouden;
    }

    public void setHuishouden(Double huishouden) {
        this.huishouden = huishouden;
    }

    public Double getInkomsten() {
        return inkomsten;
    }

    public void setInkomsten(Double inkomsten) {
        this.inkomsten = inkomsten;
    }

    public Double getKleding() {
        return kleding;
    }

    public void setKleding(Double kleding) {
        this.kleding = kleding;
    }

    public Double getMedischeKosten() {
        return medischeKosten;
    }

    public void setMedischeKosten(Double medischeKosten) {
        this.medischeKosten = medischeKosten;
    }

    public Double getOverigeUitgaven() {
        return overigeUitgaven;
    }

    public void setOverigeUitgaven(Double overigeUitgaven) {
        this.overigeUitgaven = overigeUitgaven;
    }

    public Double getTelecom() {
        return telecom;
    }

    public void setTelecom(Double telecom) {
        this.telecom = telecom;
    }

    public Double getVervoer() {
        return vervoer;
    }

    public void setVervoer(Double vervoer) {
        this.vervoer = vervoer;
    }

    public Double getVerzekeringen() {
        return verzekeringen;
    }

    public void setVerzekeringen(Double verzekeringen) {
        this.verzekeringen = verzekeringen;
    }

    public Double getVrijeTijd() {
        return vrijeTijd;
    }

    public void setVrijeTijd(Double vrijeTijd) {
        this.vrijeTijd = vrijeTijd;
    }
}
