package ai.openbanking.OpenBanking;

import ai.openbanking.OpenBanking.model.CategoryPrediction;
import ai.openbanking.OpenBanking.model.IsRecurringPrediction;
import ai.openbanking.OpenBanking.model.Transaction;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.BinomialModelPrediction;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import hex.genmodel.easy.prediction.Word2VecPrediction;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class H2OPredictor {

    HashMap<String, EasyPredictModelWrapper> models;

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

    public Transaction predictWordEmbedding(Transaction transaction) {
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
                RowData rowData = new RowData();
                rowData.put("naam", token);
                Word2VecPrediction prediction = model.predictWord2Vec(rowData);
                wordEmbeddings.add(prediction.wordEmbeddings.get("naam"));
            }

            HashMap<String, Float> averaged = new HashMap<>();
            for (int i = 0; i < wordEmbeddings.get(1).length; i++) {

                float average = 0;
                for (int j = 0; j < wordEmbeddings.size(); j++) {
                    average += wordEmbeddings.get(j)[i];
                }

                average /= wordEmbeddings.size();
                averaged.put("C"+(i+1), average);
            }

            transaction.setWordEmbedding(averaged);
        } catch (PredictException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction predictIsVasteLast(Transaction transaction) {

        if(transaction.getWordEmbedding() == null) return transaction;


        try {
            EasyPredictModelWrapper model = models.get("Binomial");

            RowData rowData = transaction.toRowData();

            BinomialModelPrediction prediction = model.predictBinomial(rowData);

            IsRecurringPrediction isRecurringPrediction = new IsRecurringPrediction();
            isRecurringPrediction.setLabel(prediction.label);
            
            isRecurringPrediction.setP0(prediction.classProbabilities[0]);
            isRecurringPrediction.setP1(prediction.classProbabilities[1]);

            transaction.setIsRecurringPrediction(isRecurringPrediction);

        } catch (PredictException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public Transaction predictCategorie(Transaction transaction) {
        if(transaction.getWordEmbedding() == null) return transaction;

        try {
            EasyPredictModelWrapper model = models.get("Multinomial");

            RowData rowData = transaction.toRowData();

            MultinomialModelPrediction prediction = model.predictMultinomial(rowData);

            CategoryPrediction categoryPrediction = new CategoryPrediction();
            categoryPrediction.setLabel(prediction.label);

            categoryPrediction.setBoodschappen(prediction.classProbabilities[0]);
            categoryPrediction.setConsumptie(prediction.classProbabilities[1]);
            categoryPrediction.setEducatie(prediction.classProbabilities[2]);
            categoryPrediction.setHuishouden(prediction.classProbabilities[3]);
            categoryPrediction.setInkomsten(prediction.classProbabilities[4]);
            categoryPrediction.setKleding(prediction.classProbabilities[5]);
            categoryPrediction.setMedischeKosten(prediction.classProbabilities[6]);
            categoryPrediction.setOverigeUitgaven(prediction.classProbabilities[7]);
            categoryPrediction.setTelecom(prediction.classProbabilities[8]);
            categoryPrediction.setVervoer(prediction.classProbabilities[9]);
            categoryPrediction.setVrijeTijd(prediction.classProbabilities[10]);

            transaction.setCategoryPrediction(categoryPrediction);

        } catch (PredictException e) {
            e.printStackTrace();
        }

        return transaction;
    }
}
