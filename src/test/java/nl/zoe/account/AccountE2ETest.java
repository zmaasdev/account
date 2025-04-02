package nl.zoe.account;

import nl.zoe.account.dto.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static nl.zoe.account.utils.JsonUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCreateAccountThenAccountIsCreated() throws Exception {
        AccountDTO accountDTO = new AccountDTO(
                "test_id",
                BigDecimal.ZERO,
                "John",
                "Doe",
                LocalDateTime.of(1970, 1, 1, 10, 30));
        mockMvc.perform(
                post("/api/v1/accounts")
                        .content(asJsonString(accountDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.customerId").value(accountDTO.getCustomerId()))
                        .andExpect(jsonPath("$.initialCredit").doesNotExist())
                        .andExpect(jsonPath("$.firstName").value(accountDTO.getFirstName()))
                        .andExpect(jsonPath("$.lastName").value(accountDTO.getLastName()))
                        .andExpect(jsonPath("$.dateOfBirth").value(accountDTO.getDateOfBirth()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
    }

    @Test
    public void givenInvalidParametersWhenCreateAccountThenBadRequest() throws Exception {
        mockMvc.perform(
                post("/api/v1/accounts")
                        .content(asJsonString(new AccountDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }
}
