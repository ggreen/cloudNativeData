package com.vmware.data.demo.retail.store.api.order;

import com.vmware.data.demo.retail.store.domain.*;
import com.vmware.data.demo.retail.store.api.customer.CustomerDao;
import com.vmware.data.demo.retail.store.api.product.ProductJdbcDao;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class OrderJdbcDAO
{
	private final JdbcTemplate jdbcTemplate;
	
	private final ProductJdbcDao productDao;
	
	private final QuerierService querierService;
	private final CustomerDao customerFavoriteDao;

	public OrderJdbcDAO(JdbcTemplate jdbcTemplate, ProductJdbcDao productDao, QuerierService querierService,
						CustomerDao customerFavoriteDao)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.productDao = productDao;
		this.querierService = querierService;
		this.customerFavoriteDao = customerFavoriteDao;
	}


	public Set<Product> selectProductsByBeacon(Beacon beacon)
	{

		return productDao.selectProductsByBeacon(beacon);
	}//------------------------------------------------
	public Set<Promotion> selectPromotionsByProduct(Product product)
	{
		
		/*
		 * startdate
			enddate
			marketingmessage
			marketingimageurl

		 */

		return productDao.selectPromotionsByProduct(product);
	}

	public Product selectProduct(int productId)
	{
		return productDao.findProductById(productId);
	}

	public List<Integer> selectProductIds()
	{
		//ResultSet rs, int rowNum

		return productDao.selectProductIds();
	}

	public Collection<Product> insertOrder(OrderDTO order)
	{
		Integer[] productIds = order.getProductIds();
		
		if (productIds == null || productIds.length == 0)
			throw new IllegalArgumentException("producdIds is required");
		
		
		String insertOrderSQL = "INSERT INTO \"pivotalmarkets\".\"orders\" (orderid,customerid,storeid,orderdate) VALUES (?,?,?,?)";
		//Get customerId
		int customerId;
		
		try
		{
			customerId = customerFavoriteDao.selectCustomerId(order.getCustomerIdentifier());
		}
		catch(EmptyResultDataAccessException e)
		{
			customerId = customerFavoriteDao.registerCustomerByIdentifer(order.getCustomerIdentifier());
			
		}
		
		//Get orderId
		int orderId = nextSeqVal("order_seq");
		int storeId = 4;
		
		this.jdbcTemplate.update(insertOrderSQL,orderId,customerId,storeId,Calendar.getInstance().getTime());
		
		String insertItemSql="INSERT INTO order_items(itemid, "+ 
										"orderid,  " + 	
										"productid, " + 
										"quantity, " + 
										"productname)" + 
		" values(nextval('item_seq'),?,?,?,?)";
		
		ArrayList<Product> products = new ArrayList<>(productIds.length );
		Product product;
		for (Integer productId : productIds)
		{
			product = productDao.findProductById(productId);
			products.add(product);

			this.jdbcTemplate.update(insertItemSql, orderId,productId,1,product.getProductName());
		}
		
		return products;
	}

	private int registerCustomerByIdentifer(CustomerIdentifier customerIdentifier)
	{


		return customerFavoriteDao.registerCustomerByIdentifer(customerIdentifier);
	}

	public int nextSeqVal(String sequenceName)
	{
		return this.jdbcTemplate.queryForObject("select nextval('"+sequenceName+"')", int.class);
	}

	public Set<ProductAssociate> selectProductAssociates(Product product)
	{

		return productDao.selectProductAssociates(product);
	}

}
