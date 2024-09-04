package dev.makos.accounts.service.impl;

import dev.makos.accounts.constants.AccountConstant;
import dev.makos.accounts.domain.dto.AccountDto;
import dev.makos.accounts.domain.dto.CustomerDto;
import dev.makos.accounts.domain.entity.Account;
import dev.makos.accounts.domain.entity.Customer;
import dev.makos.accounts.exception.BadRequestException;
import dev.makos.accounts.exception.ResourceNotFoundException;
import dev.makos.accounts.mapper.AccountMapper;
import dev.makos.accounts.mapper.CustomerMapper;
import dev.makos.accounts.repository.AccountRepository;
import dev.makos.accounts.service.AccountService;
import dev.makos.accounts.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final Random random = new Random();

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer newCustomer = customerService.create(customerDto);
        accountRepository.save(createAccount(newCustomer));
    }

    private Account createAccount(Customer customer) {
        long randomAccNumber = 1000000000L + random.nextInt(900000000);
        return Account.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(AccountConstant.SAVINGS)
                .branchAddress(AccountConstant.ADDRESS)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerService.getByMobileNumber(mobileNumber);
        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));
        return customerDto;
    }

    @Override
    public void updateAccount(CustomerDto customerDto) {
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto == null) {
            throw new BadRequestException("Account details are required to update account");
        }
        Long accountNumber = accountDto.getAccountNumber();
        if (accountNumber == null) {
            throw new BadRequestException("Account number is required to update account");
        }

        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));

        AccountMapper.mapToAccount(accountDto, account);
        accountRepository.save(account);
        customerService.update(account.getCustomerId(), customerDto);
    }

    @Override
    public void deleteByMobileNumber(String mobileNumber) {
        Customer customer = customerService.getByMobileNumber(mobileNumber);
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerService.deleteById(customer.getCustomerId());
    }
}
