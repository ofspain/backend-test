package customer.service.happiness;

public class CardNotFoundException extends RuntimeException {

  CardNotFoundException(String message) {
    super("Could not find card " + message);
  }
}
