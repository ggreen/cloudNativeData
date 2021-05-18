package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class Category {

	private String categoryId;
	private String categoryName;
	private String subCategoryId;
	private List<String> productIds;

}
