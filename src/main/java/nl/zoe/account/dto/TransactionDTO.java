package nl.zoe.account.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDTO {
    private String accountId;
    private BigDecimal amount;
}
