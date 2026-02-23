package com.banking.platform.ccsl.repository;

import com.banking.platform.ccsl.domain.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

    List<CardEntity> findByAccountNumber(String accountNumber);

    List<CardEntity> findByAccountNumberAndCardCategory(String accountNumber, String cardCategory);

    Optional<CardEntity> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);
}

