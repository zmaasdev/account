package nl.zoe.account.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

import static nl.zoe.account.utils.HttpClient.postJSON;

@Service
public class TransactionService {

    @Value("${transaction.service.uri:http://localhost:8080}")
    private String uriString;

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
}
