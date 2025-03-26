package nl.zoe.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    private BigDecimal initialCredit;
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
}
