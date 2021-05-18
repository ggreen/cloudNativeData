package com.vmware.data.demo.retail.store.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAssociate
{
	private String[] post;
	private String pre;
}
