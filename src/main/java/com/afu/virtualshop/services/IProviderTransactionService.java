package com.afu.virtualshop.services;

import com.afu.virtualshop.models.ProviderTransaction;

import java.util.List;

public interface IProviderTransactionService {
    ProviderTransaction create(ProviderTransaction providerTransaction);

    List<ProviderTransaction> saveAllProviderTransactions(List<ProviderTransaction> providerTransactionList);
}
