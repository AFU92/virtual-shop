package com.afu.virtualshop.services;

import com.afu.virtualshop.models.Customer;

public interface ICustomerService {
    Customer findById(Integer id);
}
