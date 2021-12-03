package com.doghandeveloper.doggu.account.repository;

import com.doghandeveloper.doggu.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUserName(String userName);
}
