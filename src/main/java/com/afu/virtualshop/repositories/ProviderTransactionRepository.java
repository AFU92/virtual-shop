package com.afu.virtualshop.repositories;

import com.afu.virtualshop.models.ProviderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderTransactionRepository extends JpaRepository<ProviderTransaction, String> {
}
