package ai.openbanking.OpenBanking;

import ai.openbanking.OpenBanking.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OpenBankingApplication {

	public static void main(String[] args) {
		//SpringApplication.run(OpenBankingApplication.class, args);

		H2OPredictor h2OPredictor = new H2OPredictor();
		Transaction transaction = new Transaction("3216 UT Smullers UTRECHT", (double) -56, 7,2);

		transaction = h2OPredictor.predictWordEmbedding(transaction);
		transaction = h2OPredictor.predictIsVasteLast(transaction);
		transaction = h2OPredictor.predictCategorie(transaction);

		System.out.println(transaction.toString());
	}

}
