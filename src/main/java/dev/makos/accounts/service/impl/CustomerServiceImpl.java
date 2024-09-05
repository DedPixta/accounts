package dev.makos.accounts.service.impl;

import dev.makos.accounts.domain.dto.CustomerDto;
import dev.makos.accounts.domain.entity.Customer;
import dev.makos.accounts.exception.CustomerAlreadyExistsException;
import dev.makos.accounts.exception.ResourceNotFoundException;
import dev.makos.accounts.mapper.CustomerMapper;
import dev.makos.accounts.repository.CustomerRepository;
import dev.makos.accounts.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    public static final String CUSTOMER = "Customer";
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresent(c -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
                });
        return customerRepository.save(customer);
    }

    @Override
    public Customer getByMobileNumber(String mobileNumber) {
        return customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER, "mobileNumber", mobileNumber));
    }

    @Override
    public void update(Long customerId, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(CUSTOMER, "customerId", customerId.toString()));
        CustomerMapper.mapToCustomer(customerDto, customer);
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
