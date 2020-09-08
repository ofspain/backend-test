package customer.service.happiness;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankDao bankDao;

    public List<Bank> findAll() {
        return bankDao.findAll();
    }

    public Optional<Bank> findById(Long id) {
        return bankDao.findById(id);
    }

    public Bank save(Bank bank) {
        return bankDao.save(bank);
    }

    public void deleteById(Long id) {
        bankDao.deleteById(id);
    }
}
