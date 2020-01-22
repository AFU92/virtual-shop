package com.afu.virtualshop.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The type Audit entity.
 * Superclass created to group audit fields on entities
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@MappedSuperclass
public class AuditEntity implements Serializable {
    /**
     * The Created at.
     */
    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    protected Timestamp createdAt;

    /**
     * The Updated at.
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    protected Timestamp updatedAt;

    /**
     * The Deleted at.
     */
    @Column(name = "deleted_at")
    protected Timestamp deletedAt;

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets updated at.
     *
     * @return the updated at
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets updated at.
     *
     * @param updatedAt the updated at
     */
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Gets deleted at.
     *
     * @return the deleted at
     */
    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    /**
     * Sets deleted at.
     *
     * @param deletedAt the deleted at
     */
    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
