package io.pivotal.services.pivotMart.streams.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.gemfire.domain.Product;
import io.pivotal.market.api.dao.PivotMarketPostgreDAO;
import io.pivotal.services.pivotMart.streams.PivotMartStreamConf;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(PivotMartStreamConf.class)
@Ignore
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
