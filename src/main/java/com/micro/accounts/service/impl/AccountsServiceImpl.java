package com.micro.accounts.service.impl;

import com.micro.accounts.constants.AccountsConstants;
import com.micro.accounts.dto.AccountDto;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Account;
import com.micro.accounts.entity.Customer;
import com.micro.accounts.exception.CustomerAlreadyExistsException;
import com.micro.accounts.exception.ResourceNotFoundException;
import com.micro.accounts.mapper.AccountsMapper;
import com.micro.accounts.repository.AccountsRepository;
import com.micro.accounts.repository.CustomerRepository;
import com.micro.accounts.service.AccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private static final Logger log = LoggerFactory.getLogger(AccountsServiceImpl.class);
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AccountsMapper accountsMapper;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerDto.getEmail());
        customerOptional.ifPresent(customer -> {
            throw new CustomerAlreadyExistsException("Customer already exists for email: " + customerDto.getEmail());
        });

        Customer customer = new Customer();
        accountsMapper.mapToCustomer(customerDto, customer);
        //customer.setCreatedAt(LocalDateTime.now());
        //customer.setCreatedBy("ACCOUNTS_MS");
        customer = customerRepository.save(customer);
        createNewAccount(customer);
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for mobile number:" + mobileNumber));

        CustomerDto customerDto = new CustomerDto();
        AccountDto accountDto = new AccountDto();
        accountsRepository.findByCustomerId(customer.getCustomerId())
                .ifPresentOrElse(account -> {
                            accountsMapper.mapToCustomerDto(customer, customerDto);
                            accountsMapper.mapToAccountDto(account, accountDto);
                            customerDto.setAccount(accountDto);
                        },
                        () -> {
                            throw new ResourceNotFoundException("Account not found for customer with the mobile number:" + mobileNumber);
                        });

        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findByEmail(customerDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for email:" + customerDto.getEmail()));

        CustomerDto existingCustomerDto = new CustomerDto();

        accountsMapper.mapToCustomerDto(customer, existingCustomerDto);
        if (existingCustomerDto.equals(customerDto)) {
            log.debug("Unable to update customer, no new fields are present.");
            return false;
        }
        accountsMapper.mapToCustomer(customerDto, customer);
        customerRepository.saveAndFlush(customer);
        return true;
    }

    @Override
    public void deleteCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findByEmail(customerDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for email:" + customerDto.getEmail()));

        Account account = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for customer id:" + customer.getCustomerId()));

        accountsRepository.delete(account);
        customerRepository.delete(customer);
    }


    private void createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        //newAccount.setCreatedAt(LocalDateTime.now());
        //newAccount.setCreatedBy("ACCOUNTS_MS");
        accountsRepository.save(newAccount);
    }
}
