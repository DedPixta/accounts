package dev.makos.accounts.service;

import dev.makos.accounts.domain.dto.CustomerDto;
import dev.makos.accounts.domain.entity.Customer;

public interface CustomerService {

    /**
     * Create customer
     *
     * @param customerDto CustomerDto object
     * @return Customer object
     */
    Customer create(CustomerDto customerDto);

    /**
     * Get customer by mobile number
     *
     * @param mobileNumber Mobile number
     * @return Customer object
     */
    Customer getByMobileNumber(String mobileNumber);

    /**
     * Update customer
     *
     * @param customerId   Customer id
     * @param customerDto CustomerDto object
     */
    void update(Long customerId, CustomerDto customerDto);


    /**
     * Delete customer by id
     *
     * @param id Customer id
     */
    void deleteById(Long id);
}
