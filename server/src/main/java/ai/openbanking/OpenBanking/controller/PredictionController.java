package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.H2OPredictor;
import ai.openbanking.OpenBanking.model.Transaction;
import ai.openbanking.OpenBanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {


    private final H2OPredictor predictor;
    private final TransactionRepository repository;

    @Autowired
    PredictionController(H2OPredictor predictor, TransactionRepository repository){
        this.predictor = predictor;
        this.repository = repository;
    }

    @GetMapping("/isRecurring")
    Transaction predictIsRecurring(Transaction transaction) {

        transaction = predictor.predictWordEmbedding(transaction);
        transaction = predictor.predictIsRecurring(transaction);

        return transaction;
    }

    @GetMapping("/category")
    Transaction predictCategory(Transaction transaction) {

        transaction = predictor.predictWordEmbedding(transaction);
        transaction = predictor.predictCategory(transaction);

        return transaction;
    }

    @GetMapping("/all")
    Transaction predictAll(Transaction transaction) {
        HashMap<String, Object> predictions = new HashMap<>();

        transaction = predictor.predictWordEmbedding(transaction);
        transaction = predictor.predictIsRecurring(transaction);
        transaction = predictor.predictCategory(transaction);

        return transaction;
    }

    @GetMapping("/everything")
    boolean predictEverything() {

        ArrayList<Transaction> transactions = new ArrayList<>();

        this.repository.findAll().forEach(transaction -> {

            try {
                transaction = predictor.predictWordEmbedding(transaction);
                transaction = predictor.predictIsRecurring(transaction);
                transaction = predictor.predictCategory(transaction);
            } catch (NullPointerException e){
                return;
            }

            transactions.add(transaction);
        });

        System.out.println("saving...");

        repository.saveAll(transactions);

        return true;
    }
}
