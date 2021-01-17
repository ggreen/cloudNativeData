package io.pivotal.gemfire.domain;

import java.util.Set;

public class OrderReview
{


	/**
	 * @return the productAssociates
	 */
	public Set<ProductAssociate> getProductAssociates()
	{
		return productAssociates;
	}

	/**
	 * @param productAssociates the productAssociates to set
	 */
	public void setProductAssociates(Set<ProductAssociate> productAssociates)
	{
		this.productAssociates = productAssociates;
	}

	/**
	 * @return the products
	 */
	public Set<Product> getProducts()
	{
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(Set<Product> products)
	{
		this.products = products;
	}



	private Set<Product> products;
	private Set<ProductAssociate> productAssociates;
	
}
