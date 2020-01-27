package com.afu.virtualshop.models.payu_integration;

import lombok.Data;

/**
 * The type Additional values.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class AdditionalValues {
    /**
     * The Tx value .
     */
    TxValue txValue;
    /**
     * The Tx tax.
     */
    TxTax txTax;
    /**
     * The Tx tax return base.
     */
    TxTaxReturnBase txTaxReturnBase;

}
