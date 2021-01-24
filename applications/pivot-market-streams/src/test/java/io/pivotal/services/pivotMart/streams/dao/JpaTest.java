package io.pivotal.services.pivotMart.streams.dao;

import io.pivotal.gemfire.domain.Product;
import io.pivotal.market.api.dao.PivotMarketPostgreDAO;
import io.pivotal.services.pivotMart.streams.PivotMartStreamConf;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(PivotMartStreamConf.class)
@Disabled
public class JpaTest
{
	@Autowired
	PivotMarketPostgreDAO dao;

	@Test
	public void test()
	{
		
		int productId = 1;
		
		Product p = dao.findProductById(productId);
		
		assertNotNull(p);
	}

}
