package dev.makos.accounts.service;

import dev.makos.accounts.domain.dto.CustomerDto;

public interface AccountService {

    /**
     * Create account
     *
     * @param customerDto CustomerDto object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetch account
     *
     * @param mobileNumber Mobile number
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * Update account
     *
     * @param customerDto CustomerDto object
     */
    void updateAccount(CustomerDto customerDto);


    /**
     * Delete account by mobile number
     *
     * @param mobileNumber Mobile number
     */
    void deleteByMobileNumber(String mobileNumber);

}
