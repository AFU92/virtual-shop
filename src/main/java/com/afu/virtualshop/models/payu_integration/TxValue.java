package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Tx value.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class TxValue {
    private float value;
    private String currency;

}
