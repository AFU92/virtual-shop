package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.ProviderTransaction;
import com.afu.virtualshop.repositories.ProviderTransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProviderTransactionServiceTest {
	private ProviderTransactionService providerTransactionService;

	@Mock
	private ProviderTransactionRepository providerTransactionRepository;

	@Before
	public void setUp(){
		initMocks(this);
		providerTransactionService = new ProviderTransactionService(providerTransactionRepository);
	}

	@Test
	public void create() {

		ProviderTransaction providerTransaction = new ProviderTransaction();

		when(providerTransactionRepository.save(providerTransaction)).thenReturn(providerTransaction);

		providerTransactionService.create(providerTransaction);

		verify(providerTransactionRepository).save(providerTransaction);

	}

	@Test
	public void saveAllProviderTransactions() {

		List<ProviderTransaction> providerTransactions = Arrays.asList(new ProviderTransaction());

		when(providerTransactionRepository.saveAll(providerTransactions)).thenReturn(providerTransactions);

		List<ProviderTransaction> createProviderTransactions = providerTransactionService.saveAllProviderTransactions(providerTransactions);

		verify(providerTransactionRepository).saveAll(providerTransactions);

		assertEquals(createProviderTransactions.size(), 1);

	}
}