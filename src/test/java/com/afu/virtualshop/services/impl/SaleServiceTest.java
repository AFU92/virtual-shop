package com.afu.virtualshop.services.impl;

import com.afu.virtualshop.exceptions.NotFoundException;
import com.afu.virtualshop.models.Customer;
import com.afu.virtualshop.models.Product;
import com.afu.virtualshop.models.Sale;
import com.afu.virtualshop.models.SaleProduct;
import com.afu.virtualshop.models.SaleRefundedProduct;
import com.afu.virtualshop.models.SaleStatus;
import com.afu.virtualshop.models.api.PaymentInfo;
import com.afu.virtualshop.models.api.RefundRequest;
import com.afu.virtualshop.repositories.SaleRepository;
import com.afu.virtualshop.services.ICustomerService;
import com.afu.virtualshop.services.IProductService;
import com.afu.virtualshop.services.IProviderTransactionService;
import com.afu.virtualshop.services.ISaleProductService;
import com.afu.virtualshop.services.ISaleRefundedProductService;
import com.afu.virtualshop.services.external_providers.payments.PaymentProvider;
import org.junit.Before;
import org.junit.Test; //Siempre verificar que se importe la clase correcta, suele pasar que hay muchas clases con el mismo nombre en diferentes paquetes y se termina importando la que no es
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SaleServiceTest {

	private SaleService saleService;

	@Mock
	private SaleRepository saleRepository;

	@Mock
	private IProductService productService;

	@Mock
	private PaymentProvider paymentProvider;

	@Mock
	private ICustomerService customerService;

	@Mock
	private IProviderTransactionService providerTransactionService;

	@Mock
	private ISaleProductService saleProductService;

	@Mock
	private ISaleRefundedProductService saleRefundedProductService;

	@Before
	public void setUp() {
		initMocks(this);
		saleService = new SaleService(saleRepository, productService, paymentProvider, customerService, providerTransactionService, saleProductService, saleRefundedProductService);
	}

	@Test
	public void findAll() {

		when(saleRepository.findAll()).thenReturn(Arrays.asList(new Sale()));

		List<Sale> sale = saleService.findAll();

		assertNotNull(sale);
		assertEquals(sale.size(), 1);
		verify(saleRepository).findAll();
	}

	@Test
	public void findById_whenSaleExists_thenReturnTheSale() {
		Sale sale = new Sale();
		sale.setId(1);
		when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

		Sale getSale = saleService.findById(sale.getId());

		assertNotNull(getSale);
		assertEquals(sale.getId(), getSale.getId());

		verify(saleRepository).findById(sale.getId());

	}

	@Test(expected = NotFoundException.class)
	public void findById_whenSaleNotExists_thenThrowsException() {
;
		when(saleRepository.findById(anyInt())).thenReturn(Optional.empty());

		saleService.findById(anyInt());

		verify(saleRepository).findById(anyInt());

	}

	@Test
	public void findByCustomer() {

		Customer customer = new Customer();
		customer.setId(1);

		Sale sale = new Sale();
		sale.setId(2);
		sale.setCustomer(customer);

		when(saleRepository.findByCustomerId(customer.getId())).thenReturn(Arrays.asList(sale));

		List<Sale> getSale = saleService.findByCustomer(customer.getId());

		assertNotNull(getSale);
		assertEquals(getSale.size(), 1);

		verify(saleRepository).findByCustomerId(customer.getId());
	}

	@Test
	public void updateSale() {

		Sale sale = new Sale();
		sale.setId(3);

		when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
		when(saleRepository.save(sale)).thenReturn(sale);

		Sale updateSale = saleService.update(sale);

		assertNotNull(updateSale);
		assertEquals(sale.getId(), updateSale.getId());

		verify(saleRepository).findById(sale.getId());
		verify(saleRepository).save(sale);
	}

	@Test(expected = IllegalArgumentException.class)
	public void create_whenProductsNotAvailable_throwsException() {
		Product productAvailable = new Product();
		productAvailable.setId(1);
		productAvailable.setQuantity(10);
		productAvailable.setUnitPrice(100.0F);

		SaleProduct saleProductAvailable = new SaleProduct();
		saleProductAvailable.setProduct(productAvailable);
		saleProductAvailable.setQuantity(5);

		Product productNotAvailable = new Product();
		productNotAvailable.setId(2);
		productNotAvailable.setQuantity(0);
		productNotAvailable.setUnitPrice(100.0F);

		SaleProduct saleProductNotAvailable = new SaleProduct();
		saleProductNotAvailable.setProduct(productNotAvailable);
		saleProductNotAvailable.setQuantity(5);

		Sale sale = new Sale();
		sale.setSaleProducts(Arrays.asList(saleProductAvailable, saleProductNotAvailable));

		when(productService.findById(productAvailable.getId())).thenReturn(productAvailable);
		when(productService.findById(productNotAvailable.getId())).thenReturn(productNotAvailable);
		when(productService.validateStock(productAvailable.getId(), saleProductAvailable.getQuantity())).thenReturn(true);
		when(productService.validateStock(productNotAvailable.getId(), saleProductNotAvailable.getQuantity())).thenReturn(false);

		saleService.create(sale, new PaymentInfo());
	}

	@Test
	public void create_whenPaymentRejected_thenSaveRejectedSale(){
		Sale sale = createSaleWithCommonData();

		when(paymentProvider.createPayment(any(), any())).then(invocationOnMock -> {
			sale.setStatus(SaleStatus.PAYMENT_REJECTED);
			return sale;
		});
		saleService.create(sale, new PaymentInfo());

		verifyCreateSaleCommonMocks();
		verifyNoMoreInteractions(productService);
	}

	@Test
	public void create_whenPaymentApproved_thenSaveApprovedSale(){
		Sale sale = createSaleWithCommonData();

		when(paymentProvider.createPayment(any(), any())).then(invocationOnMock -> {
			sale.setStatus(SaleStatus.PAYMENT_APPROVED);
			return sale;
		});
		saleService.create(sale, new PaymentInfo());

		verifyCreateSaleCommonMocks();
		verify(productService).reduceProductStock(anyInt(),  anyInt());
	}

	private Sale createSaleWithCommonData(){
		Customer customer = new Customer();
		customer.setId(1);

		Product productAvailable = new Product();
		productAvailable.setId(1);
		productAvailable.setQuantity(10);
		productAvailable.setUnitPrice(100.0F);

		SaleProduct saleProductAvailable = new SaleProduct();
		saleProductAvailable.setProduct(productAvailable);
		saleProductAvailable.setQuantity(5);

		when(customerService.findById(anyInt())).thenReturn(customer);
		when(productService.findById(productAvailable.getId())).thenReturn(productAvailable);
		when(productService.validateStock(productAvailable.getId(), saleProductAvailable.getQuantity())).thenReturn(true);

		Sale sale = new Sale();
		sale.setCustomer(customer);
		sale.setSaleProducts(Arrays.asList(saleProductAvailable));
		return sale;
	}

	private void verifyCreateSaleCommonMocks(){
		verify(customerService).findById(anyInt());
		verify(productService).findById(anyInt());
		verify(productService).validateStock(anyInt(), anyInt());
		verify(saleRepository, times(2)).save(any());
		verify(saleProductService).saveAll(any());
		verify(paymentProvider).createPayment(any(), any());
		verify(providerTransactionService).saveAllProviderTransactions(any());
	}


	@Test
	public void refund_whenPaymentStatusApproved_thenRefund() {
		Sale sale = createSaleWithCommonData();
		sale.setStatus(SaleStatus.PAYMENT_APPROVED);

		SaleProduct saleProduct = sale.getSaleProducts().get(0);
		saleProduct.setUnitPrice(100.0F);

		RefundRequest refundRequest = new RefundRequest();

		SaleRefundedProduct refundedProduct = new SaleRefundedProduct();
		refundedProduct.setSaleProduct(saleProduct);
		refundedProduct.setQuantity(saleProduct.getQuantity()-1);

		refundRequest.setSaleRefundedProducts(Arrays.asList(refundedProduct));
		refundRequest.setRefundReason("Something");

		when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

		when(paymentProvider.refundPayment(sale)).then(invocationOnMock -> {
			sale.setStatus(SaleStatus.REFUNDED);
			return sale;
		});

		saleService.refund(sale.getId(), refundRequest);

		verify(saleRepository).findById(sale.getId());
		verify(paymentProvider).refundPayment(sale);
		verify(providerTransactionService).saveAllProviderTransactions(any());
		verify(saleRefundedProductService).saveAll(sale.getSaleRefundedProducts());
		verify(saleRepository).save(sale);
		verify(productService).increaseProductStock(any(), any());

		assertNotNull(sale.getSaleRefundedProducts());
		assertEquals(sale.getSaleRefundedProducts().size(), 1);
		assertNotNull(sale.getRefundValue());
		assertNotNull(sale.getRefundReason());
		assertEquals(sale.getRefundReason(), refundRequest.getRefundReason());
		assertThat(sale.getRefundValue(), equalTo(refundedProduct.getQuantity()*saleProduct.getUnitPrice()));

	}

	@Test(expected = IllegalArgumentException.class)
	public void refund() {
		Sale sale = createSaleWithCommonData();
		sale.setStatus(SaleStatus.CREATED);

		when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

		saleService.refund(sale.getId(), new RefundRequest());

	}

	@Test
	public void refund_whenRefundNotApproved() {
		Sale sale = createSaleWithCommonData();
		sale.setStatus(SaleStatus.PAYMENT_APPROVED);

		SaleProduct saleProduct = sale.getSaleProducts().get(0);
		saleProduct.setUnitPrice(100.0F);

		RefundRequest refundRequest = new RefundRequest();

		SaleRefundedProduct refundedProduct = new SaleRefundedProduct();
		refundedProduct.setSaleProduct(saleProduct);
		refundedProduct.setQuantity(saleProduct.getQuantity()-1);

		refundRequest.setSaleRefundedProducts(Arrays.asList(refundedProduct));
		refundRequest.setRefundReason("Something");

		when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));

		saleService.refund(sale.getId(), refundRequest);

		verify(saleRepository).findById(sale.getId());
		verify(paymentProvider).refundPayment(sale);
		verify(providerTransactionService).saveAllProviderTransactions(any());
		verify(saleRefundedProductService).saveAll(sale.getSaleRefundedProducts());
		verify(saleRepository).save(sale);

		assertNotNull(sale.getSaleRefundedProducts());
		assertEquals(sale.getSaleRefundedProducts().size(), 1);
		assertNotNull(sale.getRefundValue());
		assertNotNull(sale.getRefundReason());
		assertEquals(sale.getRefundReason(), refundRequest.getRefundReason());
		assertThat(sale.getRefundValue(), equalTo(refundedProduct.getQuantity()*saleProduct.getUnitPrice()));

	}

}