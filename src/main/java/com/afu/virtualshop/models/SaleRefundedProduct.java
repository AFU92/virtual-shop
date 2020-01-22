package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Sale refunded product.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "sale_refunded_product")
public class SaleRefundedProduct extends AuditEntity {

    @Id
    @Column(name = "sale_refunded_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotEmpty
    @Column(name = "total_value", nullable = false)
    private String totalPrice;

    @OneToOne
    @JoinColumn(name="sale_product_id", nullable=false)
    private SaleProduct saleProduct;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale;

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
     * Gets sale product.
     *
     * @return the sale product
     */
    public SaleProduct getSaleProduct() {
        return saleProduct;
    }

    /**
     * Sets sale product.
     *
     * @param saleProduct the sale product
     */
    public void setSaleProduct(SaleProduct saleProduct) {
        this.saleProduct = saleProduct;
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
}
