package ai.openbanking.OpenBanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hex.genmodel.easy.RowData;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    private Date date;

    @ManyToOne
    @JoinColumn(name = "bank_account", nullable = false)
    private BankAccount bankAccount;

    @Transient
    @JsonIgnore
    private HashMap<String, Float> wordEmbedding;

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

        if (wordEmbedding != null) {
            for (String key: wordEmbedding.keySet()) {
                row.put(key, wordEmbedding.get(key).toString());
            }
        }

        return row;
    }

}
