package nl.zoe.account.cloud.function.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionEvent {
    private String accountId;
    private BigDecimal amount;
}
