package com.micro.accounts.mapper;

import com.micro.accounts.dto.AccountDto;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Account;
import com.micro.accounts.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AccountsMapper {

    public void mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
    }

    public void mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
    }

    public void mapToAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
    }
}
