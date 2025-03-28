package nl.zoe.account.mapper;

import nl.zoe.account.dto.AccountDTO;
import nl.zoe.account.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "initialCredit", ignore = true)
    AccountDTO accountToAccountDTO(Account account);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Account accountDTOToAccount(AccountDTO accountDTO);
}
