package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CustomerServiceTest {

	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Before
	public void setUp(){
		initMocks(this);
		customerService = new CustomerService(customerRepository);
	}

	@Test
	public void findById_whenCustomerExists_thenReturnProduct() {

		Customer customer = new Customer();
		customer.setId(10);

		when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

		Customer getCustomer = customerService.findById(customer.getId());

		assertNotNull(customer);
		assertEquals(customer.getId(), getCustomer.getId());

		verify(customerRepository).findById(customer.getId());

	}

	@Test(expected = NotFoundException.class)
	public void findById_whenCustomerNotExists_thenReturnException() {

		when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());

		customerService.findById(1);
	}
}