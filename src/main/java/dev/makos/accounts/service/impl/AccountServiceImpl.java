package dev.makos.accounts.service.impl;

import dev.makos.accounts.constants.AccountConstant;
import dev.makos.accounts.domain.dto.CustomerDto;
import dev.makos.accounts.domain.entity.Account;
import dev.makos.accounts.domain.entity.Customer;
import dev.makos.accounts.exception.CustomerAlreadyExistsException;
import dev.makos.accounts.mapper.CustomerMapper;
import dev.makos.accounts.repository.AccountRepository;
import dev.makos.accounts.repository.CustomerRepository;
import dev.makos.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresent(c -> {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
                });
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("SYSTEM");
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    private Account createAccount(Customer customer) {
        Account newAccount = new Account();
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("SYSTEM");
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountType(AccountConstant.SAVINGS);
        newAccount.setBranchAddress(AccountConstant.ADDRESS);
        return newAccount;
    }
}
