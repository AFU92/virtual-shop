package com.afu.virtualshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * The type Input product.
 *  This entity represents additions to product inventory
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Entity
@Table(name = "input_product")
public class InputProduct extends AuditEntity {

    @Id
    @Column(name = "input_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    private String nitProvider;

    private String billNumber;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

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
     * Gets nit provider.
     *
     * @return the nit provider
     */
    public String getNitProvider() {
        return nitProvider;
    }

    /**
     * Sets nit provider.
     *
     * @param nitProvider the nit provider
     */
    public void setNitProvider(String nitProvider) {
        this.nitProvider = nitProvider;
    }

    /**
     * Gets bill number.
     *
     * @return the bill number
     */
    public String getBillNumber() {
        return billNumber;
    }

    /**
     * Sets bill number.
     *
     * @param billNumber the bill number
     */
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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
}
