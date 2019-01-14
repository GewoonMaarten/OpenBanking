package ai.openbanking.OpenBanking.exception;

public class PhoneSubscriptionNotFoundException extends RuntimeException {
    public PhoneSubscriptionNotFoundException(Integer id) {
        super("Could not find phone subscription " + id);
    }
}