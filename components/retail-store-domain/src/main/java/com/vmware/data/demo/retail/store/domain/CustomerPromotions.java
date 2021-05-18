package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class CustomerPromotions {

	private String customerId;
	private Map<String, CustomerCategory> customerCategories;

}
