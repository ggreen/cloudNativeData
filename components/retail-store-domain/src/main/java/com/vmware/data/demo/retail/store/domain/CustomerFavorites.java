package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class CustomerFavorites
{
	private Collection<ProductQuantity> productQuanties;
	private int customerId;
}
