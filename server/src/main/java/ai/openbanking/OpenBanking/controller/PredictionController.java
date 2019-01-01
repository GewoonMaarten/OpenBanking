package ai.openbanking.OpenBanking.controller;

import ai.openbanking.OpenBanking.H2OPredictor;
import ai.openbanking.OpenBanking.model.CategoryPrediction;
import ai.openbanking.OpenBanking.model.IsRecurringPrediction;
import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {


    private final H2OPredictor predictor;

    @Autowired
    PredictionController(H2OPredictor predictor){ this.predictor = predictor; }

    @GetMapping("/isRecurring")
    IsRecurringPrediction predictIsRecurring(Transaction transaction) {

        transaction = predictor.predictWordEmbedding(transaction);
        IsRecurringPrediction prediction = predictor.predictIsRecurring(transaction);

        return prediction;
    }

    @GetMapping("/category")
    CategoryPrediction predictCategory(Transaction transaction) {

        transaction = predictor.predictWordEmbedding(transaction);
        CategoryPrediction prediction = predictor.predictCategory(transaction);

        return prediction;
    }

    @GetMapping("/all")
    HashMap<String, Object> predictAll(Transaction transaction) {
        HashMap<String, Object> predictions = new HashMap<>();


        transaction = predictor.predictWordEmbedding(transaction);
        CategoryPrediction categoryPrediction = predictor.predictCategory(transaction);
        IsRecurringPrediction isRecurringPrediction = predictor.predictIsRecurring(transaction);

        predictions.put("category", categoryPrediction);
        predictions.put("isRecurring", isRecurringPrediction);

        return predictions;
    }
}
