package com.banking.platform.mdm.repository;

import com.banking.platform.mdm.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Account} entities.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByPhone(String phone);

    List<Account> findByNameContainingIgnoreCase(String name);

    List<Account> findByEmailIgnoreCase(String email);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}

