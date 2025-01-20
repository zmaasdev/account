package nl.zoe.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    private BigDecimal initialCredit;
}
