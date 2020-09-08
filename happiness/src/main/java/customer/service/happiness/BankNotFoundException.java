package customer.service.happiness;

public class BankNotFoundException extends RuntimeException {

  BankNotFoundException(Long id) {
    super("Could not find bank " + id);
  }
}
