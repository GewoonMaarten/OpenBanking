package ai.openbanking.OpenBanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hex.genmodel.easy.RowData;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;

@Data
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double amount;
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "bank_account", nullable = false)
    @JsonBackReference
    private BankAccount bankAccount;

    @Transient
    @JsonIgnore
    private HashMap<String, Float> wordEmbedding;

    public RowData toRowData(){

        String day  = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonth());

        RowData row = new RowData();
        row.put("naam", name);
        row.put("bedrag", amount);
        row.put("dag", day);
        row.put("maand", month);

        if (wordEmbedding != null) {
            for (String key: wordEmbedding.keySet()) {
                row.put(key, wordEmbedding.get(key).toString());
            }
        }

        return row;
    }

}
