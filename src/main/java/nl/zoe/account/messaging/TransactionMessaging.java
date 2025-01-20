package nl.zoe.account.messaging;

import nl.zoe.account.dto.TransactionDTO;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class TransactionMessaging {

    private final AccountRepository accountRepository;

    public TransactionMessaging(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public Consumer<TransactionDTO> receiveTransaction() {
        return transactionDTO -> {
           Account account = accountRepository.getReferenceById(transactionDTO.getAccountId());
           account.setBalance(account.getBalance().add(transactionDTO.getAmount()));
           accountRepository.save(account);
        } ;
    }
}
