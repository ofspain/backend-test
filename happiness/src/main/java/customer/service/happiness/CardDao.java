package customer.service.happiness;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface CardDao extends JpaRepository<Card, Long> {

  @Query(value = "SELECT c FROM Card c WHERE c.cardNumber = ?1")
  Optional<Card> findByNUmber(String numer);
}
