package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.models.api.CreditCardTokenRequest;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.repositories.CreditCardTokenRepository;
import com.afu.virtualshop.services.ICreditCardTokenService;
import com.afu.virtualshop.services.ICustomerService;
import com.afu.virtualshop.services.external_providers.payments.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardTokenService implements ICreditCardTokenService {

    private final CreditCardTokenRepository creditCardTokenRepository;

    private final ICustomerService customerService;

    private final PaymentProvider paymentProvider;

    @Override
    public CreditCardToken create(CreditCardTokenRequest creditCardTokenRequest) {
        Customer customer = this.customerService.findById(creditCardTokenRequest.getCustomer().getId());
        CreditCardToken creditCardToken = this.paymentProvider.createToken(creditCardTokenRequest.getPaymentInfo(), customer);
        return this.creditCardTokenRepository.save(creditCardToken);
    }

    @Override
    public List<CreditCardToken> findAll() {
        return creditCardTokenRepository.findAll();
    }

    @Override
    public CreditCardToken getById(String tokenId) {
        return this.creditCardTokenRepository.findById(tokenId).orElseThrow(() -> new NotFoundException("Token with Id " + tokenId + " not found"));
    }

    @Override
    public List<CreditCardToken> findAllByCustomer(Customer customer) {
        return creditCardTokenRepository.findAllByCustomer(customer);
    }

    @Override
    public PaymentInfo getTokenPaymentInfo(String tokenId, Integer customerId) {
        CreditCardToken creditCardToken = this.getById(tokenId);
        if(creditCardToken.getCustomer().getId().equals(customerId)){
            return paymentProvider.getTokenInfo(creditCardToken);
        } else {
            throw new IllegalArgumentException("Token with Id: " + tokenId + " doesn't belongs to the customer: " + customerId);
        }
    }
}
