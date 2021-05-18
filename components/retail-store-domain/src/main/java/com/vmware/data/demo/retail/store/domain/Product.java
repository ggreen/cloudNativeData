package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class Product {

	private int productId;
	private String productName;
	private String categoryId;
	private String subCategoryId;
	private BigDecimal unit;
	private BigDecimal cost;
	private BigDecimal price;
	private Date startDate;
	private Date endDate;
	private Date createdDate;
	private Date lastUpdatedDate;
}
