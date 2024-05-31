package com.micro.accounts.service.impl;

import com.micro.accounts.constants.AccountsConstants;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Account;
import com.micro.accounts.entity.Customer;
import com.micro.accounts.exception.CustomerAlreadyExistsException;
import com.micro.accounts.mapper.AccountsMapper;
import com.micro.accounts.repository.AccountsRepository;
import com.micro.accounts.repository.CustomerRepository;
import com.micro.accounts.service.AccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AccountsMapper accountsMapper;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerDto.getEmail());
        if(customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists for email: " + customerDto.getEmail());
        }
        Customer customer = new Customer();
        accountsMapper.mapToCustomer(customerDto, customer);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Admin");
        customer = customerRepository.save(customer);
        createNewAccount(customer);
    }

    private void createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Admin");
        accountsRepository.save(newAccount);
    }
}
