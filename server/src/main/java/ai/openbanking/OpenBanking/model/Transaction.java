package ai.openbanking.OpenBanking.model;

import hex.genmodel.easy.RowData;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double amount;
    private String name;
    private Date date;

    @ManyToOne
    private IsRecurringPrediction isRecurringPrediction;
    @ManyToOne
    private CategoryPrediction categoryPrediction;

    private HashMap<String, Float> wordEmbedding;

    public Transaction(Double amount, String name, Date date) {
        this.amount = amount;
        this.name = name;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public IsRecurringPrediction getIsRecurringPrediction() {
        return isRecurringPrediction;
    }

    public void setIsRecurringPrediction(IsRecurringPrediction isRecurringPrediction) {
        this.isRecurringPrediction = isRecurringPrediction;
    }

    public CategoryPrediction getCategoryPrediction() {
        return categoryPrediction;
    }

    public void setCategoryPrediction(CategoryPrediction categoryPrediction) {
        this.categoryPrediction = categoryPrediction;
    }

    public HashMap<String, Float> getWordEmbedding() {
        return wordEmbedding;
    }

    public void setWordEmbedding(HashMap<String, Float> wordEmbedding) {
        this.wordEmbedding = wordEmbedding;
    }

    public RowData toRowData(){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String day  = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(cal.get(Calendar.MONTH));

        RowData row = new RowData();
        row.put("naam", name);
        row.put("bedrag", amount);
        row.put("dag", day);
        row.put("maand", month);

        if (isRecurringPrediction != null) {
            row.put("is_vaste_last", isRecurringPrediction);
        }

        if (categoryPrediction != null) {
            row.put("categorie", categoryPrediction);
        }

        if (wordEmbedding != null) {
            for (String key: wordEmbedding.keySet()) {
                row.put(key, wordEmbedding.get(key).toString());
            }
        }

        return row;
    }

}
