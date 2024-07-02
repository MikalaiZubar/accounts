package com.micro.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accountsAuditorAware")
public class AccountsAuditorAware implements AuditorAware<String> {

    /**
     * Used to handle @CreatedBy @LastModifiedBy @CreatedDate @LastModifiedDate
     * annotations for BaseEntity class
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
