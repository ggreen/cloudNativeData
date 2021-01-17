package io.pivotal.market.api.dao;


import org.springframework.beans.factory.annotation.Autowired;

import io.pivotal.gemfire.domain.Product;

public class PivotMarketPostgreDAO
{
	@Autowired
	ProductRepository productRepository;
	
	public Product findProductById(int productId)
	{
		return productRepository.findById(productId).get();
	}

}
