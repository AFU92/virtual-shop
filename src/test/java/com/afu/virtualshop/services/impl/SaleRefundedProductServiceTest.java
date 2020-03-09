package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.models.SaleProduct;
import com.afu.virtualshop.models.SaleRefundedProduct;
import com.afu.virtualshop.repositories.SaleProductRepository;
import com.afu.virtualshop.repositories.SaleRefundedProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SaleRefundedProductServiceTest {

	private SaleRefundedProductService saleRefundedProductService;

	@Mock
	private SaleRefundedProductRepository saleRefundedProductRepository;

	@Before
	public void setUp(){
		initMocks(this);
		saleRefundedProductService = new SaleRefundedProductService(saleRefundedProductRepository);
	}

	@Test
	public void saveAll() {

		List<SaleRefundedProduct> saleRefundedProducts = Arrays.asList(new SaleRefundedProduct());

		when(saleRefundedProductRepository.saveAll(saleRefundedProducts)).thenReturn(saleRefundedProducts);

		List<SaleRefundedProduct> createRefundedSaleProducts = saleRefundedProductService.saveAll(saleRefundedProducts);

		verify(saleRefundedProductRepository).saveAll(saleRefundedProducts);

		assertEquals(createRefundedSaleProducts.size(), 1);

	}
}