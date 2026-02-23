package com.banking.platform.ccsl.repository;

import com.banking.platform.ccsl.domain.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(String token);

    Optional<TokenEntity> findByAccountNumber(String accountNumber);
}

