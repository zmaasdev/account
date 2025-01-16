package nl.zoe.account.service;

import nl.zoe.account.dto.AccountDTO;
import nl.zoe.account.mapper.AccountMapper;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public AccountService(AccountMapper accountMapper, AccountRepository repository, TransactionService transactionService) {
        this.accountMapper = accountMapper;
        this.accountRepository = repository;
    }

    public AccountDTO create(AccountDTO accountDTO) {
        Account newAccount = accountMapper.accountDTOToAccount(accountDTO);
        return this.accountMapper.accountToAccountDTO(this.accountRepository.save(newAccount));
    }
}
