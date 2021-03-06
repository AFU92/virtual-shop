package com.afu.virtualshop.controllers;

import com.afu.virtualshop.documentationControllers.CreditCardTokenDocumentation;
import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.api.CreditCardTokenRequest;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.services.ICreditCardTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Credit card token controller.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@AllArgsConstructor
@RestController
@RequestMapping("/credit_card_tokens")
public class CreditCardTokenController implements CreditCardTokenDocumentation {

    private final ICreditCardTokenService creditCardTokenService;

    @PostMapping
    public ResponseEntity<CreditCardToken> create(@RequestBody CreditCardTokenRequest creditCardTokenRequest){
        CreditCardToken creditCardToken = this.creditCardTokenService.create(creditCardTokenRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardToken);
    }

    @GetMapping("/{tokenId}/payment_info")
    public PaymentInfo getTokenPaymentInfo(@PathVariable String tokenId, @RequestBody CreditCardTokenRequest creditCardTokenRequest){
        return this.creditCardTokenService.getTokenPaymentInfo(tokenId, creditCardTokenRequest.getCustomer().getId());
    }
}
