package io.pivotal.gemfire.domain;

import java.util.Collection;

public class CustomerFavorites
{
	private Collection<ProductQuantity> productQuanties;
	private int customerId;
	/**
	 * @return the productQuanties
	 */
	public Collection<ProductQuantity> getProductQuanties()
	{
		return productQuanties;
	}
	
	/**
	 * @param productQuanties the productQuanties to set
	 */
	public void setProductQuanties(Collection<ProductQuantity> productQuanties)
	{
		this.productQuanties = productQuanties;
	}

	/**
	 * @return the customerId
	 */
	public int getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId)
	{
		this.customerId = customerId;
	}
	
	
}
