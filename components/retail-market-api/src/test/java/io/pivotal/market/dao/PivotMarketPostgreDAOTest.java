package io.pivotal.market.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import com.vmware.data.retail.store.domain.Product;
import io.pivotal.market.api.dao.PivotMarketPostgreDAO;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class PivotMarketPostgreDAOTest
{

	@Test
	public void findProductById()
	{
		Product expected = new JavaBeanGeneratorCreator<Product>(Product.class)
				.randomizeAll().create();
		JdbcTemplate template = mock(JdbcTemplate.class);
		when(template.query(anyString(),any(ResultSetExtractor.class))).thenReturn(expected);

		PivotMarketPostgreDAO dao = new PivotMarketPostgreDAO(template);
		
		int productId = 1;
		
		Product actual = dao.findProductById(productId);
		
		assertNotNull(actual);


		assertEquals(expected,actual);
	}

}
