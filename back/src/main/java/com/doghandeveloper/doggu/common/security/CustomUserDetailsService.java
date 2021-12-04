package com.doghandeveloper.doggu.common.security;

import com.doghandeveloper.doggu.account.domain.Account;
import com.doghandeveloper.doggu.account.domain.UserAccount;
import com.doghandeveloper.doggu.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> {
            return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. email: " + email);
        });
        return new UserAccount(account);
    }
}
