package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.H2OPredictor;
import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {

    @RequestMapping("isvastelast")
    public Transaction predictIsVasteLast(Transaction transaction) {
        H2OPredictor h2OPredictor = new H2OPredictor();

        transaction = h2OPredictor.predictWordEmbedding(transaction);
        transaction = h2OPredictor.predictIsVasteLast(transaction);

        return transaction;
    }

    @RequestMapping("categorie")
    public Transaction predictCategorie(Transaction transaction) {
        H2OPredictor h2OPredictor = new H2OPredictor();

        transaction = h2OPredictor.predictWordEmbedding(transaction);
        transaction = h2OPredictor.predictCategorie(transaction);

        return transaction;
    }

    @RequestMapping("all")
    public Transaction predictAll(Transaction transaction) {
        H2OPredictor h2OPredictor = new H2OPredictor();

        transaction = h2OPredictor.predictWordEmbedding(transaction);
        transaction = h2OPredictor.predictIsVasteLast(transaction);
        transaction = h2OPredictor.predictCategorie(transaction);

        return transaction;
    }
}
