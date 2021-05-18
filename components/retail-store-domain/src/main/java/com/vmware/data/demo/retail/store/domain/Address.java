package com.vmware.data.demo.retail.store.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

	private String addressLine1;
	private String addressLine2;
	private String stateCode;
	private String zipCode;

}
