package ai.openbanking.OpenBanking;

import ai.openbanking.OpenBanking.model.Category;
import ai.openbanking.OpenBanking.model.Transaction;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import hex.genmodel.easy.prediction.Word2VecPrediction;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.stream.Stream;

@Component
public class H2OPredictor {

    private HashMap<String, EasyPredictModelWrapper> models;

    public H2OPredictor(){
        models = loadModels();
    }

    private HashMap<String, EasyPredictModelWrapper> loadModels(){
        HashMap<String, EasyPredictModelWrapper> models = new HashMap<>();

        try(Stream<Path> paths = Files.walk(
                Paths.get(getClass().getClassLoader().getResource("models").toURI())
        )) {

            paths.filter(Files::isRegularFile).forEach(p -> {
                EasyPredictModelWrapper model;
                try {
                    model = new EasyPredictModelWrapper(MojoModel.load(p.toString()));
                    models.put(model.getModelCategory().toString(), model);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return models;
    }

    public Transaction predictWordEmbedding(Transaction transaction) throws NullPointerException {
;
        try {
            EasyPredictModelWrapper model = models.get("WordEmbedding");

            String[] tokens = transaction
                    .getName()
                    .toLowerCase()
                    .replaceAll("\\b\\w\\b", "")
                    .replaceAll("\\d+", "")
                    .trim()
                    .split("\\W+");

            ArrayList<float[]> wordEmbeddings = new ArrayList<>();

            for(String token : tokens) {
                if (token.length() <= 0) continue;

                RowData rowData = new RowData();
                rowData.put("naam", token);

                Word2VecPrediction prediction = model.predictWord2Vec(rowData);
                wordEmbeddings.add(prediction.wordEmbeddings.get("naam"));
            }

            HashMap<String, Float> averaged = new HashMap<>();
            for (int i = 0; i < wordEmbeddings.get(0).length; i++) {

                float average = 0;
                for (float[] wordEmbedding : wordEmbeddings) {
                    average += wordEmbedding[i];
                }

                average /= wordEmbeddings.size();
                averaged.put("C"+(i+1), average);
            }

            transaction.setWordEmbedding(averaged);

        } catch (PredictException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            throw e;
        }

        return transaction;
    }

    public Transaction predictIsRecurring(Transaction transaction) {

        try {
            EasyPredictModelWrapper model = models.get("Binomial");

            RowData rowData = transaction.toRowData();

            BinomialModelPrediction prediction = model.predictBinomial(rowData);
            transaction.setIsRecurring(Boolean.valueOf(prediction.label));

        } catch (PredictException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction predictCategory(Transaction transaction) {
        try {
            EasyPredictModelWrapper model = models.get("Multinomial");

            RowData rowData = transaction.toRowData();

            MultinomialModelPrediction prediction = model.predictMultinomial(rowData);

            transaction.setCategory(Category.fromString(prediction.label));

        } catch (PredictException e) {
            e.printStackTrace();
        }

        return transaction;
    }
}
