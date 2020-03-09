package com.afu.virtualshop.documentationControllers;

import com.afu.virtualshop.models.CreditCardToken;
import com.afu.virtualshop.models.api.CreditCardTokenRequest;
import com.afu.virtualshop.models.api.PaymentInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface CreditCardTokenDocumentation {


	@ApiOperation("Create a token")
	ResponseEntity <CreditCardToken> create(CreditCardTokenRequest creditCardTokenRequest);

	@ApiOperation("Get a token")
	PaymentInfo getTokenPaymentInfo(String tokenId,
			CreditCardTokenRequest creditCardTokenRequest);


	}


