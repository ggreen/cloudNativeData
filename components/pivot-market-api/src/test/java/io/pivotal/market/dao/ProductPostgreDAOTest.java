package io.pivotal.market.dao;

import io.pivotal.gemfire.domain.Product;
import io.pivotal.market.api.order.OrderJdbcDAO;
import io.pivotal.market.api.product.ProductJdbcDao;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductPostgreDAOTest
{

	private ProductJdbcDao subject;

	@Mock
	private OrderJdbcDAO orderJdbcDAO;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp()
	{
		subject = new ProductJdbcDao(orderJdbcDAO ,jdbcTemplate);
	}

	@Test
	public void findProductById()
	{
		Product expected = new JavaBeanGeneratorCreator<Product>(Product.class)
				.randomizeAll().create();
		when(jdbcTemplate.query(anyString(),any(ResultSetExtractor.class))).thenReturn(expected);


		int productId = 1;
		
		Product actual = subject.findProductById(productId);
		
		assertNotNull(actual);


		Assertions.assertEquals(expected,actual);
	}

}
