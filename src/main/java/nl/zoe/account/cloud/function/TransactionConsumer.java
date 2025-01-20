package nl.zoe.account.cloud.function;

import nl.zoe.account.cloud.function.event.TransactionEvent;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class TransactionConsumer implements Consumer<TransactionEvent> {

    private final AccountRepository accountRepository;

    public TransactionConsumer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void accept(TransactionEvent transactionEvent) {
        Account account = accountRepository.getReferenceById(transactionEvent.getAccountId());
        account.setBalance(account.getBalance().add(transactionEvent.getAmount()));
        accountRepository.save(account);
    }
}
