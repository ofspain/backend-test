package customer.service.happiness;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;

/**
   import org.springframework.data.domain.Page;
   import org.springframework.data.domain.Pageable;
   import org.springframework.data.domain.PageRequest;
**/
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class CardApi {

    private final CardService cardService;
    private final BankService bankService;



    @GetMapping("/")
    public Map<String, Object> homeInfor(){

      Map<String,Object> mainload = new HashMap<>();

      String general = "Endpoints for adding,getting by id and multiple for"+
                     "entity Card and Bank Customer is implemented but not exposed.";
      String post_card = "issue post request to /cards/";
      String get_card_by_id = "Issue get request to /cards/{id_of_card}";
      String post_bank = "issue post request to /banks/";
      String get_bank_by_id = "issue get request to /banks/{id_of_bank}";
      String get_multiple = "issue get request respectively to /banks/ and /cards/";

      String question1 = "issue get request to /card-scheme/verify/{number}";
      String question2 = "issue get request to /card-scheme/stats";
      String last_note = "Bank must exsist before a card can be posted as it is"+
                                  "required attribute of card";

     mainload.put("GENERAL_INFOR", general);
     mainload.put("POST_CARD", post_card);
     mainload.put("GET_BY_ID_CARD", get_card_by_id);
     mainload.put("POST_BANK", post_bank);
     mainload.put("GET_BY_ID_BANK", get_bank_by_id);
     mainload.put("QUESTION_1",question1);
     mainload.put("QUESTION_2",question2);
     mainload.put("NOTE",last_note);



      return mainload;
    }





    @GetMapping("/cards/")
    public CollectionModel<EntityModel<Card>> findAllCards(
                        @RequestParam(defaultValue = "0") Integer pageNo,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "id") String sortBy) {

        Map<String,Object> cardsData = cardService.getAllCards(pageNo, pageSize, sortBy);
        List<Card> cardList = (List<Card>)cardsData.get("CARDS");

        List<EntityModel<Card>> cards = cardList.stream()
             .map(card -> EntityModel.of(card,
                      linkTo(methodOn(CardApi.class).findByCardId(card.getId())).withSelfRel(),
                       linkTo(methodOn(CardApi.class).findAllCards(pageNo,pageSize,sortBy)).withRel("cards")))
                      .collect(Collectors.toList());

        return CollectionModel.of(cards, linkTo(methodOn(CardApi.class).findAllCards(pageNo,pageSize,sortBy)).withSelfRel());
    }

    @GetMapping("/card-scheme/verify/{number}")
    public Map<String, Object> verifyCard(@PathVariable String number){

      Card card = cardService.findByCardNumber(number) //
              .orElseThrow(() -> new CardNotFoundException("with number "+number));

      Map<String,Object> mainload = new HashMap<>();
      Boolean success = card.getExpire().compareTo(new Date()) >= 0;
      mainload.put("success",success);

      Map<String,String> payload = new HashMap<>();
      payload.put("scheme", card.getScheme().getName());
      payload.put("type", card.getType().getName());
      payload.put("bank", card.getBank().getCode());

      mainload.put("payload", payload);

      return mainload;
    }

    @GetMapping("/card-scheme/stats")
    public Map<String, Object> cardsStats(
                        @RequestParam(defaultValue = "0", value="start") Integer pageNo,
                        @RequestParam(defaultValue = "3", value="limit") Integer pageSize,
                        @RequestParam(defaultValue = "id") String sortBy){

        Map<String,Object> cardsData = cardService.getAllCards(pageNo, pageSize, sortBy);
        List<Card> cards = (List<Card>)cardsData.get("CARDS");
        Long count = (Long)cardsData.get("COUNT");

        Map<String,Object> mainload = new HashMap<>();

        mainload.put("success",count > 0);
        mainload.put("start",pageNo);
        mainload.put("limit",pageSize);
        mainload.put("size",count);

        Map<String,Integer> payload = new HashMap<>();
        for(Card card : cards){
          payload.put(card.getCardNumber(), card.getHit());
        }
        mainload.put("payload", payload);

        return mainload;


    }

    @GetMapping("/cards/{id}")
    public EntityModel<Card> findByCardId(@PathVariable Long id) {
      Card card = cardService.findById(id) //
              .orElseThrow(() -> new CardNotFoundException(String.format("with id %d", id)));

      return EntityModel.of(card, //
         linkTo(methodOn(CardApi.class).findByCardId(id)).withSelfRel());

    }

    @PostMapping("/banks/")
    public Bank newBank(@RequestBody Bank newBank) {
         return bankService.save(newBank);
    }

    @PostMapping("/cards/")
    public Card newCard(@RequestBody Card newCard) {
         return cardService.save(newCard);
    }

    @GetMapping("/banks/{id}")
    public EntityModel<Bank> findByBankId(@PathVariable Long id) {
         Bank bank = bankService.findById(id) //
                 .orElseThrow(() -> new BankNotFoundException(id));

         return EntityModel.of(bank, //
            linkTo(methodOn(CardApi.class).findByBankId(id)).withSelfRel(),
            linkTo(methodOn(CardApi.class).findBankAll()).withRel("banks"));
    }

    @GetMapping("/banks/")
    public CollectionModel<EntityModel<Bank>> findBankAll() {

         List<EntityModel<Bank>> banks = bankService.findAll().stream()
            .map(bank -> EntityModel.of(bank,
                     linkTo(methodOn(CardApi.class).findByBankId(bank.getId())).withSelfRel(),
                      linkTo(methodOn(CardApi.class).findBankAll()).withRel("banks")))
                     .collect(Collectors.toList());

         return CollectionModel.of(banks, linkTo(methodOn(CardApi.class).findBankAll()).withSelfRel());

    }


  }
