package io.pivotal.gemfire.domain;

import java.beans.Transient;
import java.util.HashMap;

public class CustomerPromotions {

	private String customerId;
	private HashMap<String, CustomerCategory> customerCategories;

	public CustomerPromotions() {
	}

	public CustomerPromotions(String customerId, HashMap<String, CustomerCategory> customerCategories) {
		this.customerId = customerId;
		this.customerCategories = customerCategories;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public HashMap<String, CustomerCategory> getCustomerCategories() {
		return customerCategories;
	}

	public void setCustomerCategories(HashMap<String, CustomerCategory> customerCategories) {
		this.customerCategories = customerCategories;
	}

	@Transient
	public String getKey() {
		return this.customerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerCategories == null) ? 0 : customerCategories.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerPromotions other = (CustomerPromotions) obj;
		if (customerCategories == null) {
			if (other.customerCategories != null)
				return false;
		} else if (!customerCategories.equals(other.customerCategories))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerPromotions [customerId=" + customerId + ", customerCategories=" + customerCategories + "]";
	}

}
