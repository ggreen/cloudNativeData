package com.vmware.data.demo.retail.store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerIdentifier
{
	private String key;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
}
