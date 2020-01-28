package com.afu.virtualshop.models.payu_integration;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Additional values.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data
@AllArgsConstructor
public class AdditionalValue {
    private float value;
    private String currency;
}
