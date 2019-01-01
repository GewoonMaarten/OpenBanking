package ai.openbanking.OpenBanking.exception;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(Integer id) {
        super("Could not find bank account " + id);
    }
}

