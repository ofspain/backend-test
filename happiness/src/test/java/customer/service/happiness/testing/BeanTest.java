package customer.service.happiness.testing;

import customer.service.happiness.Card;
import customer.service.happiness.CardDao;
import customer.service.happiness.CardService;

import customer.service.happiness.Bank;
import customer.service.happiness.BankDao;
import customer.service.happiness.BankService;
import java.util.Date;
import customer.service.happiness.Scheme;
import customer.service.happiness.Type;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BeanTest {

  @MockBean
  private CardDao cardDao;
  @Autowired
  private CardService cardService;

  @MockBean
  private BankDao bankDao;
  @Autowired
  private BankService bankService;

  @Test
  void testRegisterBank(){

    // given  '{"name": "United Bank of Africa", "code": "UBA"}'
    Bank bank = new Bank("United Bank of Africa", "UBA");
    given(bankDao.save(any(Bank.class))).willReturn(bankEntity(1L));

    // when
    Long bankId = bankService.save(bank).getId();

    // then
    assertThat(bankId).isEqualTo(1L);
  }


  @Test
  void testRegisterCard(){

    // "scheme" : "VISA", "type" : "DEBIT", "cardNumber" :"124567894329",
    //"expire" : "2021-12-17", "bank":{"id": 1 }
    Bank bank = new Bank("United Bank of Africa", "UBA");
    bank = bankService.save(bank);

    Card card = new Card(Scheme.VISA, Type.DEBIT,bank, "124567894329", new Date());
    given(cardDao.save(any(Card.class))).willReturn(cardEntity(2L, bank));

    // when
    Long cardId = cardService.save(card).getId();

    // then
    assertThat(cardId).isEqualTo(2L);
  }


  private Bank bankEntity(Long id){
    Bank newBank = new Bank();
    newBank.setId(id);
    newBank.setName("United Bank of Africa");
    newBank.setCode("UBA");
    return newBank;
  }

  private Card cardEntity(Long id, Bank bank){
    Card newCard = new Card();
    newCard.setId(id);
    newCard.setCardNumber("United Bank of Africa");
    newCard.setScheme(Scheme.VISA);
    newCard.setExpire(new Date());
    newCard.setBank(bank);
    newCard.setType(Type.DEBIT);
    return newCard;
  }
}
