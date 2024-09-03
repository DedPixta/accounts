package dev.makos.accounts.service;

import dev.makos.accounts.domain.dto.CustomerDto;

public interface AccountService {

    /**
     * Create account
     *
     * @param customerDto CustomerDto object
     */
    void createAccount(CustomerDto customerDto);
}
