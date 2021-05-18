package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class OrderReview
{
	private Set<Product> products;
	private Set<ProductAssociate> productAssociates;
	
}
