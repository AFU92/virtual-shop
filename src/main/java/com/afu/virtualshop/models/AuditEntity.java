package com.afu.virtualshop.models;

import lombok.Data;
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
@Data
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

}
