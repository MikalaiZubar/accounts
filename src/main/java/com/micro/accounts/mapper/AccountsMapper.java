package com.micro.accounts.mapper;

import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class AccountsMapper {

    public void mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
    }
}
