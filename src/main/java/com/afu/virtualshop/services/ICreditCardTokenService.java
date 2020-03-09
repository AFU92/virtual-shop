package com.afu.virtualshop.services;

import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.models.api.CreditCardTokenRequest;
import com.afu.virtualshop.models.api.PaymentInfo;

import java.util.List;

public interface ICreditCardTokenService {

    CreditCardToken create(CreditCardTokenRequest creditCardTokenRequest);

    List<CreditCardToken> findAll();

    CreditCardToken getById(String tokenId);

    List<CreditCardToken> findAllByCustomer(Customer customer);

    PaymentInfo getTokenPaymentInfo(String tokenId, Integer customerId);
}
