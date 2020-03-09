package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.ProductCategory;
import com.afu.virtualshop.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest {

	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Before
	public void setUp(){
		initMocks(this);
		productService = new ProductService(productRepository);
	}

	@Test
	public void findAll() {

		when(productRepository.findAll()).thenReturn(Arrays.asList(new Product()));

		List<Product> products = productService.findAll();

		assertNotNull(products);
		assertEquals(products.size(), 1);

		verify(productRepository).findAll();
	}

	@Test
	public void findAllAvailable() {

		Product productAvailable = new Product();
		productAvailable.setId(1);
		productAvailable.setQuantity(10);

		Product productNotAvailable = new Product();
		productNotAvailable.setId(2);
		productNotAvailable.setQuantity(0);

		when(productRepository.findAll()).thenReturn(Arrays.asList(productAvailable, productNotAvailable));

		List<Product> availableProducts = productService.findAllAvailable();

		assertNotNull(availableProducts);
		assertEquals(availableProducts.size(), 1);
		assertTrue(availableProducts.contains(productAvailable));

		verify(productRepository).findAll();
	}

	@Test
	public void findById_whenProductExists_thenReturnProduct() {
		Product product = new Product();
		product.setId(15);

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

		Product getProduct = productService.findById(product.getId());

		assertNotNull(product);
		assertEquals(product.getId(), getProduct.getId());

		verify(productRepository).findById(product.getId());

	}

	@Test(expected = NotFoundException.class)
	public void findById_whenProductNotExists_thenReturnException() {

		when(productRepository.findById(anyInt())).thenReturn(Optional.empty());

		productService.findById(1);
	}

	@Test
	public void findByProductCategory() {

		ProductCategory phoneCategory = ProductCategory.PHONES;

		Product product = new Product();
		product.setProductCategory(phoneCategory);

		when(productRepository.findByProductCategory(phoneCategory)).thenReturn(Arrays.asList(product));

		List<Product> products = productService.findByProductCategory(phoneCategory);

		assertNotNull(products);
		assertEquals(products.size(), 1);
		assertTrue(products.contains(product));

		verify(productRepository).findByProductCategory(phoneCategory);

	}

	@Test
	public void update() {

		Product product = new Product();
		product.setId(1);

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		when(productRepository.save(product)).thenReturn(product);

		Product updateProduct = productService.update(product);

		assertNotNull(updateProduct);
		assertEquals(product.getId(), updateProduct.getId());

		verify(productRepository).findById(product.getId());
		verify(productRepository).save(product);

	}

	@Test
	public void create() {

		Product product = new Product();

		when(productRepository.save(product)).thenReturn(product);

		Product createProduct = productService.create(product);

		verify(productRepository).save(product);

	}

	@Test
	public void validateStock_whenQuantityProductExists_thenReturnProduct() {
		Product product = new Product();
		product.setId(1);
		product.setQuantity(10);

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

		Boolean validProduct = productService.validateStock(product.getId(), 5);

		assertEquals(validProduct, true);

		verify(productRepository).findById(product.getId());

	}

	@Test
	public void validateStock_whenQuantityProductNotExists_thenReturnProduct() {
		Product product = new Product();
		product.setId(2);
		product.setQuantity(0);

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

		Boolean validProduct = productService.validateStock(product.getId(), 5);

		assertEquals(validProduct, false);

		verify(productRepository).findById(product.getId());

	}

	@Test
	public void reduceProductStock() {

		Product product = new Product();
		product.setId(1);
		product.setQuantity(10);

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		when(productRepository.save(product)).thenReturn(product);

		productService.reduceProductStock(product.getId(), 5);

		assertThat(product.getQuantity(), equalTo(5));

		verify(productRepository).findById(product.getId());
		verify(productRepository).save(product);

	}

	@Test
	public void increaseProductStock() {

		Product product = new Product();
		product.setId(1);
		product.setQuantity(10);

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		when(productRepository.save(product)).thenReturn(product);

		productService.increaseProductStock(product.getId(), 5);

		assertThat(product.getQuantity(), equalTo(15));

		verify(productRepository).findById(product.getId());
		verify(productRepository).save(product);
	}
}