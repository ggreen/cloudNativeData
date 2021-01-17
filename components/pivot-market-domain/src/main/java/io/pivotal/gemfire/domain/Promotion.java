package io.pivotal.gemfire.domain;

import java.beans.Transient;
import java.util.Date;

public class Promotion  {

	private int promotionId;
	private Date startDate;
	private Date endDate;
	private String marketingMessage;
	private String marketingUrl;
	private int productId;

	public Promotion() {
	}

	/**
	 * @return the promotionId
	 */
	public int getPromotionId()
	{
		return promotionId;
	}



	/**
	 * @param promotionId the promotionId to set
	 */
	public void setPromotionId(int promotionId)
	{
		this.promotionId = promotionId;
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

	public String getMarketingMessage() {
		return marketingMessage;
	}

	public void setMarketingMessage(String marketingMessage) {
		this.marketingMessage = marketingMessage;
	}

	@Transient
	public String getKey() {
		return String.valueOf(this.promotionId);
	}
	
	/**
	 * @return the marketingUrl
	 */
	public String getMarketingUrl()
	{
		return marketingUrl;
	}
	
	/**
	 * @param marketingUrl the marketingUrl to set
	 */
	public void setMarketingUrl(String marketingUrl)
	{
		this.marketingUrl = marketingUrl;
	}


	/**
	 * @return the productId
	 */
	public int getProductId()
	{
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((marketingMessage == null) ? 0 : marketingMessage.hashCode());
		result = prime * result + ((marketingUrl == null) ? 0 : marketingUrl.hashCode());
		result = prime * result + productId;
		result = prime * result + promotionId;
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		Promotion other = (Promotion) obj;
		if (endDate == null)
		{
			if (other.endDate != null)
				return false;
		}
		else if (!endDate.equals(other.endDate))
			return false;
		if (marketingMessage == null)
		{
			if (other.marketingMessage != null)
				return false;
		}
		else if (!marketingMessage.equals(other.marketingMessage))
			return false;
		if (marketingUrl == null)
		{
			if (other.marketingUrl != null)
				return false;
		}
		else if (!marketingUrl.equals(other.marketingUrl))
			return false;
		if (productId != other.productId)
			return false;
		if (promotionId != other.promotionId)
			return false;
		if (startDate == null)
		{
			if (other.startDate != null)
				return false;
		}
		else if (!startDate.equals(other.startDate))
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
		builder.append("Promotion [promotionId=").append(promotionId).append(", startDate=").append(startDate)
		.append(", endDate=").append(endDate).append(", marketingMessage=").append(marketingMessage)
		.append(", marketingUrl=").append(marketingUrl).append(", productId=").append(productId).append("]");
		return builder.toString();
	}
	
}
