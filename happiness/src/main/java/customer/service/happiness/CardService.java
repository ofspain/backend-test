package customer.service.happiness;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardDao cardDao;

    public List<Card> findAll() {
        return cardDao.findAll();
    }

    public Optional<Card> findById(Long id) {
        return cardDao.findById(id);
    }

    public Card save(Card card) {
        return cardDao.save(card);
    }

    public void deleteById(Long id) {
        cardDao.deleteById(id);
    }

    public Map<String,Object> getAllCards(Integer pageNo,
                                    Integer pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));


        Page<Card> pagedResult = cardDao.findAll(paging);

        Long totalPage = pagedResult.getTotalElements();
        List<Card> cards = new ArrayList<>();

        if(pagedResult.hasContent()) {
            cards = pagedResult.getContent();
        }
        Map<String,Object> result = new HashMap<>();
        result.put("COUNT",totalPage);
        result.put("CARDS",cards);
        return result;

    }

    public Optional<Card> findByCardNumber(String number){
      return cardDao.findByNUmber(number);
    }
}
