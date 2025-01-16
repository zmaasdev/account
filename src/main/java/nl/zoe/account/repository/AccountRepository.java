package nl.zoe.account.repository;

import nl.zoe.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> { }
