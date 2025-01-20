package nl.zoe.account.cloud.function;

import nl.zoe.account.cloud.function.event.TransactionEvent;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class TransactionFunction {

    private final AccountRepository accountRepository;

    public TransactionFunction(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public Consumer<TransactionEvent> receiveTransaction() {
        return transactionEvent -> {
           Account account = accountRepository.getReferenceById(transactionEvent.getAccountId());
           account.setBalance(account.getBalance().add(transactionEvent.getAmount()));
           accountRepository.save(account);
        } ;
    }
}
