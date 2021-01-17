package io.pivotal.market.dao;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import io.pivotal.gemfire.domain.Product;
import io.pivotal.market.api.dao.PivotMarketPostgreDAO;

@Ignore
public class PivotMarketPostgreDAOTest
{

	@Test
	public void test()
	{
		PivotMarketPostgreDAO dao = new PivotMarketPostgreDAO();
		
		int productId = 1;
		
		Product p = dao.findProductById(productId);
		
		assertNotNull(p);
	}

}
