package ai.openbanking.OpenBanking.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Integer id) {
        super("Could not find transaction " + id);
    }
    public TransactionNotFoundException(String category) {
        super("Could not find transaction by" + category);
    }
}
