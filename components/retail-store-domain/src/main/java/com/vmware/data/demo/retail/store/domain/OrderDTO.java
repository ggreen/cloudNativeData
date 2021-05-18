package com.vmware.data.demo.retail.store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO
{
	private CustomerIdentifier customerIdentifier;
	private Integer[] productIds;

}
