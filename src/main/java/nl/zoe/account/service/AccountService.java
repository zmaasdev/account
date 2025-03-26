package nl.zoe.account.service;

import lombok.RequiredArgsConstructor;
import nl.zoe.account.event.TransactionEvent;
import nl.zoe.account.dto.AccountDTO;
import nl.zoe.account.mapper.AccountMapper;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import nl.zoe.account.utils.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final StreamBridge streamBridge;
    @Value("${transaction.topic}")
    private String topic;

    public AccountDTO create(AccountDTO accountDTO) {
        Account newAccount = this.accountRepository.save(accountMapper.accountDTOToAccount(accountDTO));
        if (BigDecimalUtils.isGreaterThanZero(accountDTO.getInitialCredit())) {
            streamBridge.send(topic, new TransactionEvent(newAccount.getId(), accountDTO.getInitialCredit(), LocalDateTime.now()));
        }
        return this.accountMapper.accountToAccountDTO(newAccount);
    }
}
