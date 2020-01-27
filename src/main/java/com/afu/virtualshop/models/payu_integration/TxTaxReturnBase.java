package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Tx tax return base.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class TxTaxReturnBase {
    private float value;
    private String currency;

}
