package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerCategory {

	private String productId;
	private String categoryId;
	private String promotionId;
	private int weight;
}
