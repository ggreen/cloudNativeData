package io.pivotal.gemfire.domain;

import java.util.Arrays;

public class OrderDTO
{
	private CustomerIdentifier customerIdentifier;
	private Integer[] productIds;
	
	public OrderDTO() {}
	
	public OrderDTO(CustomerIdentifier customerIdentifier, Integer[] productIds)
	{
		this.customerIdentifier= customerIdentifier;
		
		this.productIds = productIds;
	}//------------------------------------------------


	/**
	 * @return the productIds
	 */
	public Integer[] getProductIds()
	{
		return productIds;
	}
	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(Integer[] productIds)
	{
		this.productIds = productIds;
	}

	/**
	 * @return the customerIdentifier
	 */
	public CustomerIdentifier getCustomerIdentifier()
	{
		return customerIdentifier;
	}

	/**
	 * @param customerIdentifier the customerIdentifier to set
	 */
	public void setCustomerIdentifier(CustomerIdentifier customerIdentifier)
	{
		this.customerIdentifier = customerIdentifier;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDTO [customerIdentifier=").append(customerIdentifier).append(", productIds=")
		.append(Arrays.toString(productIds)).append("]");
		return builder.toString();
	}

	
	
}
