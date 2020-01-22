package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sale.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "sale")
public class Sale extends AuditEntity {

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @Column(name = "refund_percent", nullable = false)
    private Integer refundPercent;

    @Column(name = "refund_value", nullable = false)
    private String refundValue;

    @Column(nullable = false)
    private SaleStatus status;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @OneToMany(mappedBy="sale")
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @OneToMany(mappedBy="sale")
    private List<SaleRefundedProduct> saleRefundedProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public String getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets total price.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets refund percent.
     *
     * @return the refund percent
     */
    public Integer getRefundPercent() {
        return refundPercent;
    }

    /**
     * Sets refund percent.
     *
     * @param refundPercent the refund percent
     */
    public void setRefundPercent(Integer refundPercent) {
        this.refundPercent = refundPercent;
    }

    /**
     * Gets refund value.
     *
     * @return the refund value
     */
    public String getRefundValue() {
        return refundValue;
    }

    /**
     * Sets refund value.
     *
     * @param refundValue the refund value
     */
    public void setRefundValue(String refundValue) {
        this.refundValue = refundValue;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public SaleStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    /**
     * Gets shipping address.
     *
     * @return the shipping address
     */
    public String getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets shipping address.
     *
     * @param shippingAddress the shipping address
     */
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Gets sale products.
     *
     * @return the sale products
     */
    public List<SaleProduct> getSaleProducts() {
        return saleProducts;
    }

    /**
     * Sets sale products.
     *
     * @param saleProducts the sale products
     */
    public void setSaleProducts(List<SaleProduct> saleProducts) {
        this.saleProducts = saleProducts;
    }

    /**
     * Gets sale refunded products.
     *
     * @return the sale refunded products
     */
    public List<SaleRefundedProduct> getSaleRefundedProducts() {
        return saleRefundedProducts;
    }

    /**
     * Sets sale refunded products.
     *
     * @param saleRefundedProducts the sale refunded products
     */
    public void setSaleRefundedProducts(List<SaleRefundedProduct> saleRefundedProducts) {
        this.saleRefundedProducts = saleRefundedProducts;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets products.
     *
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets products.
     *
     * @param products the products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
