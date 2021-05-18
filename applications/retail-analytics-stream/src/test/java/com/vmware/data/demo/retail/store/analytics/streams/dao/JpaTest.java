package com.vmware.data.demo.retail.store.analytics.streams.dao;

import com.vmware.data.demo.retail.store.analytics.streams.AppConf;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.market.api.product.ProductJdbcDao;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(AppConf.class)
@Disabled
public class JpaTest
{
	@Autowired
	ProductJdbcDao dao;

	@Test
	public void test()
	{
		
		int productId = 1;
		
		Product p = dao.findProductById(productId);
		
		assertNotNull(p);
	}

}
