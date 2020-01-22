package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Sale product.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "sale_product")
public class SaleProduct extends AuditEntity {

    @Id
    @Column(name = "sale_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "unit_price", nullable = false)
    private String unitPrice;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotEmpty
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;

    @OneToOne(mappedBy = "saleProduct")
    private SaleRefundedProduct saleRefundedProduct;

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
     * Gets unit price.
     *
     * @return the unit price
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets unit price.
     *
     * @param unitPrice the unit price
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets sale.
     *
     * @return the sale
     */
    public Sale getSale() {
        return sale;
    }

    /**
     * Sets sale.
     *
     * @param sale the sale
     */
    public void setSale(Sale sale) {
        this.sale = sale;
    }

    /**
     * Gets sale refunded product.
     *
     * @return the sale refunded product
     */
    public SaleRefundedProduct getSaleRefundedProduct() {
        return saleRefundedProduct;
    }

    /**
     * Sets sale refunded product.
     *
     * @param saleRefundedProduct the sale refunded product
     */
    public void setSaleRefundedProduct(SaleRefundedProduct saleRefundedProduct) {
        this.saleRefundedProduct = saleRefundedProduct;
    }
}
