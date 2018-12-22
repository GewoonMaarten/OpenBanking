package ai.openbanking.OpenBanking.model;

import hex.genmodel.easy.RowData;

import java.util.HashMap;
import java.util.Optional;

public class Transaction {

    private String naam;
    private Double bedrag;
    private Integer dag;
    private Integer maand;

    private Integer is_vaste_last;
    private String categorie;

    private HashMap<String, Float> wordEmbedding;

    public Transaction(String naam, Double bedrag, Integer dag, Integer maand) {
        this.naam = naam;
        this.bedrag = bedrag;
        this.dag = dag;
        this.maand = maand;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Double getBedrag() {
        return bedrag;
    }

    public void setBedrag(Double bedrag) {
        this.bedrag = bedrag;
    }

    public Integer getDag() {
        return dag;
    }

    public void setDag(Integer dag) {
        this.dag = dag;
    }

    public Integer getMaand() {
        return maand;
    }

    public void setMaand(Integer maand) {
        this.maand = maand;
    }

    public Integer getIs_vaste_last() {
        return is_vaste_last;
    }

    public void setIs_vaste_last(Integer is_vaste_last) {
        this.is_vaste_last = is_vaste_last;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public HashMap<String, Float> getWordEmbedding() {
        return wordEmbedding;
    }

    public void setWordEmbedding(HashMap<String, Float> wordEmbedding) {
        this.wordEmbedding = wordEmbedding;
    }

    public RowData toRowData(){
        RowData row = new RowData();
        row.put("naam", naam);
        row.put("bedrag", bedrag);
        row.put("dag", dag.toString());
        row.put("maand", maand.toString());

        if (is_vaste_last != null) {
            row.put("is_vaste_last", is_vaste_last);
        }

        if (categorie != null) {
            row.put("categorie", categorie);
        }

        if (wordEmbedding != null) {
            for (String key: wordEmbedding.keySet()) {
                row.put(key, wordEmbedding.get(key).toString());
            }
        }

        return row;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "naam='" + naam + '\'' +
                ", bedrag=" + bedrag +
                ", dag=" + dag +
                ", maand=" + maand +
                ", is_vaste_last=" + is_vaste_last +
                ", categorie='" + categorie + '\'' +
                ", wordEmbedding=" + wordEmbedding +
                '}';
    }
}
