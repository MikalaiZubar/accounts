package com.micro.accounts.service;

import com.micro.accounts.dto.CustomerDto;

public interface AccountsService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateCustomer(CustomerDto customerDto);

    void deleteCustomer(CustomerDto customerDto);
}
