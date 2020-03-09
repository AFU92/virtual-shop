package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.repositories.CustomerRepository;
import com.afu.virtualshop.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer findById(Integer id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer with Id " + id + " not found"));
    }
}
