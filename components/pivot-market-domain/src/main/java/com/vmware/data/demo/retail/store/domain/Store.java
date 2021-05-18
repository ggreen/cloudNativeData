package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Store {

	private String storeId;
	private String name;
	private Address address;
	private double longitude;
	private double latitude;
}
