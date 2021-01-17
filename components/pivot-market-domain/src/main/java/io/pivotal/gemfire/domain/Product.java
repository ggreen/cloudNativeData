package io.pivotal.gemfire.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Product {

	private int productId;
	private String productName;
	private String categoryId;
	private String subCategoryId;
	private BigDecimal unit;
	private BigDecimal cost;
	private BigDecimal price;
	private Date startDate;
	private Date endDate;
	private Date createdDate;
	private Date lastUpdatedDate;

	public Product() {
	}

	public Product(int productId, String productName, String categoryId, String subCategoryId, BigDecimal unit,
			BigDecimal cost, BigDecimal price, Date startDate, Date endDate, Date createdDate, Date lastUpdatedDate) {
		this.productId = productId;
		this.productName = productName;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.unit = unit;
		this.cost = cost;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((lastUpdatedDate == null) ? 0 : lastUpdatedDate.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + productId;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((subCategoryId == null) ? 0 : subCategoryId.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (categoryId == null)
		{
			if (other.categoryId != null)
				return false;
		}
		else if (!categoryId.equals(other.categoryId))
			return false;
		if (cost == null)
		{
			if (other.cost != null)
				return false;
		}
		else if (!cost.equals(other.cost))
			return false;
		if (createdDate == null)
		{
			if (other.createdDate != null)
				return false;
		}
		else if (!createdDate.equals(other.createdDate))
			return false;
		if (endDate == null)
		{
			if (other.endDate != null)
				return false;
		}
		else if (!endDate.equals(other.endDate))
			return false;
		if (lastUpdatedDate == null)
		{
			if (other.lastUpdatedDate != null)
				return false;
		}
		else if (!lastUpdatedDate.equals(other.lastUpdatedDate))
			return false;
		if (price == null)
		{
			if (other.price != null)
				return false;
		}
		else if (!price.equals(other.price))
			return false;
		if (productId != other.productId)
			return false;
		if (productName == null)
		{
			if (other.productName != null)
				return false;
		}
		else if (!productName.equals(other.productName))
			return false;
		if (startDate == null)
		{
			if (other.startDate != null)
				return false;
		}
		else if (!startDate.equals(other.startDate))
			return false;
		if (subCategoryId == null)
		{
			if (other.subCategoryId != null)
				return false;
		}
		else if (!subCategoryId.equals(other.subCategoryId))
			return false;
		if (unit == null)
		{
			if (other.unit != null)
				return false;
		}
		else if (!unit.equals(other.unit))
			return false;
		return true;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Product [productId=").append(productId).append(", productName=").append(productName)
		.append(", categoryId=").append(categoryId).append(", subCategoryId=").append(subCategoryId).append(", unit=")
		.append(unit).append(", cost=").append(cost).append(", price=").append(price).append(", startDate=")
		.append(startDate).append(", endDate=").append(endDate).append(", createdDate=").append(createdDate)
		.append(", lastUpdatedDate=").append(lastUpdatedDate).append("]");
		return builder.toString();
	}

}
