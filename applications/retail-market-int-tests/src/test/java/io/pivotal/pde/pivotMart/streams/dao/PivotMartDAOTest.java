package io.pivotal.pde.pivotMart.streams.dao;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import io.pivotal.gemfire.domain.Beacon;
import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.pde.pivotMart.streams.PivotMartStreamConf;

public class PivotMartDAOTest
{
	private static PivotMartDAO dao;
	
	@BeforeClass
	public static void setUp()
	{
		String jdbcUrl  = "jdbc:postgresql://localhost:5432/template1";
		String userName = "gpadmin";
		String password = Config.getProperty("pwd");
		
		PivotMartStreamConf conf = new PivotMartStreamConf();
		dao = new PivotMartDAO();
		dao.jdbcTemplate = conf.jdbcTemplate(conf.dataSource(jdbcUrl, userName, password));
	}

	@Test
	public void testCustomerFavorites()
	{
		CustomerIdentifier customer = new CustomerIdentifier();
		customer.setKey("1006");
		CustomerFavorites cp = dao.selectCustomerFavorites(customer).iterator().next();
		assertNotNull(cp);
	}//------------------------------------------------
	
	@Test
	public void testSelectPromotionsByBeacon()
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
		
		//Customer customer;
		Beacon beacon  = new Beacon();
		int major = 23;
		int minor = 1;
		String uuid = "6a468e3e-631f-44e6-8620-cc83330ed994";
		beacon.setMajor(major);
		beacon.setMinor(minor);
		beacon.setUuid(uuid);
		
		Collection<Product> products = dao.selectProductsByBeacon(beacon);
		assertNotNull(products);
		assertTrue(!products.isEmpty());
	}
	@Test
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
