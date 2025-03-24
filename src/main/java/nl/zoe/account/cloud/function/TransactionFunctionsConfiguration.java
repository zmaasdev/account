package nl.zoe.account.cloud.function;

import lombok.RequiredArgsConstructor;
import nl.zoe.account.cloud.function.event.TransactionEvent;
import nl.zoe.account.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class TransactionFunctionsConfiguration {

    private final TransactionService transactionService;

    @Bean
    public Consumer<Message<TransactionEvent>> poll() {
        return transactionService::processTransactionEvent;
    }
}
