package io.pivotal.services.pivotMart.streams.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import io.pivotal.gemfire.domain.Beacon;
import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.services.pivotMart.streams.PivotMartStreamConf;
import io.pivotal.services.pivotMart.streams.dao.PivotMartDAO;

@Ignore
public class PivotMartDAOTest
{
	private static PivotMartDAO dao;
	
	@BeforeClass
	public static void setUp()
	{
		PivotMartStreamConf conf = new PivotMartStreamConf();
		dao = new PivotMartDAO();
		dao.jdbcTemplate = conf.jdbcTemplate(mock(DataSource.class));
	}

	@Test
	public void testCustomerFavorites()
	{
		CustomerIdentifier customer = new CustomerIdentifier();
		customer.setFirstName("Joe");
		customer.setLastName("Smith");
		CustomerFavorites cp = dao.selectCustomerFavorites(customer).iterator().next();
		assertNotNull(cp);
		
		assertTrue(cp.getProductQuanties() != null && !cp.getProductQuanties().isEmpty());
		
		
		assertTrue(cp
				.getProductQuanties()
					.stream()
						.allMatch(p -> p.getProduct() != null && p.getProduct().getProductName() != null && p.getProduct().getProductName().length() > 0));
		
	}//------------------------------------------------
	
	@Test
	public void testSelectProductsByBeacon()
	{
		/*
		 * customerId int,
		  deviceId text,
		  major int,
		  minor int,
		  signalPower int,
		  promotionID int,
		  marketingMessage text,
		  marketingimageurl text
		 */
		Beacon beacon  = new Beacon();
		int major = -1;
		int minor = -1;
		String uuid = "2";
		beacon.setMajor(major);
		beacon.setMinor(minor);
		beacon.setUuid(uuid);
		
		Collection<Product> products = dao.selectProductsByBeacon(beacon);
		assertNotNull(products);
		assertTrue(!products.isEmpty());
	}
	@Test
	@Ignore
	public void testSelectPromotionsByProduct()
	{
		Product product = null;
		
		Collection<Promotion> promotions = dao.selectPromotionsByProduct(product);
		
		assertNull(promotions);
		int wonderBreadId = 58;
		
		 product = new Product();
		product.setProductId(wonderBreadId);
		
		promotions = dao.selectPromotionsByProduct(product);
		
		assertNotNull(promotions);
		assertTrue(!promotions.isEmpty());
		
		assertTrue(promotions.stream().allMatch(p -> p.getMarketingMessage().contains("Bread")));
	}
	
}
