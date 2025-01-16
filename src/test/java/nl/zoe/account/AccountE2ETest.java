package nl.zoe.account;

import nl.zoe.account.dto.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static nl.zoe.account.utils.JsonUtils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCreateAccountThenAccountIsCreated() throws Exception {
        mockMvc.perform(
                post("/api/v1/accounts")
                        .content(asJsonString(new AccountDTO("test_id")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated())
                .andDo( print())
                        .andExpect( jsonPath("$.customerId").value("test_id"));
    }
}
