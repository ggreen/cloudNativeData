package io.pivotal.gemfire.domain;

public class ProductQuantity
{
	private int quantity;
	private Product product;
	
	public ProductQuantity() {}
	
	public ProductQuantity(int quantity, Product product)
	{
		super();
		this.quantity = quantity;
		this.product = product;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity()
	{
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	/**
	 * @return the product
	 */
	public Product getProduct()
	{
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product)
	{
		this.product = product;
	}
	
}
