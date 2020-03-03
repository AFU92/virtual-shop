package com.afu.virtualshop.models;

import com.afu.virtualshop.models.hibernate.JsonDataUserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * The type Provider transaction.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data
@Entity
@Table(name = "provider_transaction")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
public class ProviderTransaction extends AuditEntity {

    @Id
    @NotNull
    @Column(name = "provider_transaction_id")
    String providerTransactionId;

    @NotEmpty
    private String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionResult result;

    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    @JsonIgnore
    private Sale sale;

    @Column(name = "provider_response")
    @Type(type = "JsonDataUserType")
    private Map<String, Object> providerResponse;

}
