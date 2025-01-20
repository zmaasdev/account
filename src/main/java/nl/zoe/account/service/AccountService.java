package nl.zoe.account.service;

import nl.zoe.account.dto.AccountDTO;
import nl.zoe.account.mapper.AccountMapper;
import nl.zoe.account.model.Account;
import nl.zoe.account.repository.AccountRepository;
import nl.zoe.account.utils.BigDecimalUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    public AccountService(AccountMapper accountMapper, AccountRepository repository, TransactionService transactionService) {
        this.accountMapper = accountMapper;
        this.accountRepository = repository;
        this.transactionService = transactionService;
    }

    public AccountDTO create(AccountDTO accountDTO) {
        Account newAccount = accountMapper.accountDTOToAccount(accountDTO);
        if (BigDecimalUtils.isGreaterThanZero(accountDTO.getInitialCredit())) {
            transactionService.createTransaction(newAccount.getId(), newAccount.getBalance());
        }
        return this.accountMapper.accountToAccountDTO(this.accountRepository.save(newAccount));
    }
}
