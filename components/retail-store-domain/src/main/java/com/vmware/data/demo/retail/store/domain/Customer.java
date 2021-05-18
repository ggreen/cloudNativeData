package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.Date;

@Data
@NoArgsConstructor
public class Customer {

	private int customerId;
	private String firstName;
	private String lastName;
	private Address address;
	private String primaryNumber;
	private String mobileNumber;
	private Date openDate;
	private Date lastUpdate;

}
