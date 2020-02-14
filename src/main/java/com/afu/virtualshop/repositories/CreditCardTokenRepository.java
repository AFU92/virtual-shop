package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardTokenRepository extends JpaRepository<CreditCardToken, String> {
    List<CreditCardToken> findAllByCustomer(Customer customer);
}
