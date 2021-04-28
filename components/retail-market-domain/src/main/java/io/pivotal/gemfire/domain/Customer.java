package io.pivotal.gemfire.domain;

import java.beans.Transient;
import java.util.Date;

public class Customer {

	private int customerId;
	private String firstName;
	private String lastName;
	private Address address;
	private String primaryNumber;
	private String mobileNumber;
	private Date openDate;
	private Date lastUpdate;
	
	public Customer() {}

	public Customer(String firstName, String lastName, Address address, String primaryNumber, String mobileNumber,
			Date openDate, Date lastUpdate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.primaryNumber = primaryNumber;
		this.mobileNumber = mobileNumber;
		this.openDate = openDate;
		this.lastUpdate = lastUpdate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPrimaryNumber() {
		return primaryNumber;
	}

	public void setPrimaryNumber(String primaryNumber) {
		this.primaryNumber = primaryNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + customerId;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((openDate == null) ? 0 : openDate.hashCode());
		result = prime * result + ((primaryNumber == null) ? 0 : primaryNumber.hashCode());
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
		Customer other = (Customer) obj;
		if (address == null)
		{
			if (other.address != null)
				return false;
		}
		else if (!address.equals(other.address))
			return false;
		if (customerId != other.customerId)
			return false;
		if (firstName == null)
		{
			if (other.firstName != null)
				return false;
		}
		else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null)
		{
			if (other.lastName != null)
				return false;
		}
		else if (!lastName.equals(other.lastName))
			return false;
		if (lastUpdate == null)
		{
			if (other.lastUpdate != null)
				return false;
		}
		else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (mobileNumber == null)
		{
			if (other.mobileNumber != null)
				return false;
		}
		else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (openDate == null)
		{
			if (other.openDate != null)
				return false;
		}
		else if (!openDate.equals(other.openDate))
			return false;
		if (primaryNumber == null)
		{
			if (other.primaryNumber != null)
				return false;
		}
		else if (!primaryNumber.equals(other.primaryNumber))
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
		builder.append("Customer [customerId=").append(customerId).append(", firstName=").append(firstName)
		.append(", lastName=").append(lastName).append(", address=").append(address).append(", primaryNumber=")
		.append(primaryNumber).append(", mobileNumber=").append(mobileNumber).append(", openDate=").append(openDate)
		.append(", lastUpdate=").append(lastUpdate).append("]");
		return builder.toString();
	}

	@Transient
	public String getKey()
	{
		return String.valueOf(customerId);
	}

	
}
