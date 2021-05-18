package com.vmware.data.demo.retail.store.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.Date;

@Data
@NoArgsConstructor
public class Promotion  {

	private int promotionId;
	private Date startDate;
	private Date endDate;
	private String marketingMessage;
	private String marketingUrl;
	private int productId;

}
