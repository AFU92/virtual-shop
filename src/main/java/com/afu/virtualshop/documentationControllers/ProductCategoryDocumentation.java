package com.afu.virtualshop.documentationControllers;

import com.afu.virtualshop.models.ProductCategory;
import io.swagger.annotations.ApiOperation;


import java.util.List;

public interface ProductCategoryDocumentation {

	@ApiOperation("Get all ProductCategories")
	List<ProductCategory> findAll();

}


