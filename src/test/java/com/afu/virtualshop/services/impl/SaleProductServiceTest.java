package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.SaleProduct;
import com.afu.virtualshop.repositories.SaleProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SaleProductServiceTest {

	private SaleProductService saleProductService;

	@Mock
	private SaleProductRepository saleProductRepository;

	@Before
	public void setUp(){
		initMocks(this);
		saleProductService = new SaleProductService(saleProductRepository);
	}

	@Test
	public void saveAll() {

		List<SaleProduct> saleProducts = Arrays.asList(new SaleProduct());

		when(saleProductRepository.saveAll(saleProducts)).thenReturn(saleProducts);

		List<SaleProduct> createSaleProducts = saleProductService.saveAll(saleProducts);

		verify(saleProductRepository).saveAll(saleProducts);

		assertEquals(createSaleProducts.size(), 1);

	}
}