package com.afu.virtualshop.controllers;

import com.afu.virtualshop.documentationControllers.CustomerDocumentation;
import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.services.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Customer controller.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController implements CustomerDocumentation {

    private final ICustomerService customerService;

    @GetMapping("/{customerId}/tokens")
    public List<CreditCardToken> customerTokens(@PathVariable Integer customerId) {
        Customer customer = this.customerService.findById(customerId);
        return customer.getCreditCardTokens();
    }
}
