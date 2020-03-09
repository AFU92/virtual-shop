package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.ProviderTransaction;
import com.afu.virtualshop.repositories.ProviderTransactionRepository;
import com.afu.virtualshop.services.IProviderTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProviderTransactionService implements IProviderTransactionService {

    private final ProviderTransactionRepository providerTransactionRepository;


    @Override
    public ProviderTransaction create(ProviderTransaction providerTransaction) {
        return this.providerTransactionRepository.save(providerTransaction);
    }

    @Override
    public List<ProviderTransaction> saveAllProviderTransactions(List<ProviderTransaction> providerTransactionList) {
        return this.providerTransactionRepository.saveAll(providerTransactionList);
    }
}
