package com.afu.virtualshop.documentationControllers;

import com.afu.virtualshop.models.CreditCardToken;
import io.swagger.annotations.ApiOperation;


import java.util.List;

public interface CustomerDocumentation {

	@ApiOperation("Get all tokens by user")
	List<CreditCardToken> customerTokens(Integer customerId);
}


