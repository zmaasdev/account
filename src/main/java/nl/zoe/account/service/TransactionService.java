package nl.zoe.account.service;

import lombok.RequiredArgsConstructor;
import nl.zoe.account.cloud.function.event.TransactionEvent;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

import static nl.zoe.account.utils.HttpClient.postJSON;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Value("${transaction.service.uri:http://localhost:8080}")
    private String uriString;
    private final AccountRepository accountRepository;

    public void createTransaction(String accountID, BigDecimal initialCredit) {
        Runnable task = () -> {
            var uri = URI.create(uriString);
            Map<String, String> requestBody = Map.of("accountID", accountID, "initialCredit", initialCredit.toString());
            try {
                postJSON(uri, requestBody);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Thread.startVirtualThread(task);
    }

    @Transactional
    public void processTransactionEvent(Message<TransactionEvent> event) {
        Account account = accountRepository.getReferenceById(event.getPayload().getAccountId());
        account.setBalance(event.getPayload().getAmount());
        accountRepository.save(account);
    }
}
