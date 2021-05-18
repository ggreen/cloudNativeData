package com.vmware.data.demo.retail.store.analytics.streams.dao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vmware.data.demo.retail.store.api.product.ProductJdbcDao;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import io.pivotal.gemfire.domain.Beacon;
import io.pivotal.gemfire.domain.Customer;
import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.gemfire.domain.ProductQuantity;
import io.pivotal.gemfire.domain.Promotion;
import nyla.solutions.core.util.Text;

public class RetailAnalyticsDAO
{
	private final JdbcTemplate jdbcTemplate;
	private final ProductJdbcDao pivotMarketPostgreDAO;
	
	private final QuerierService querierService;
	private String countSql = "select count(*) from pivotalmarkets.orders";

	public RetailAnalyticsDAO(JdbcTemplate jdbcTemplate, ProductJdbcDao pivotMarketPostgreDAO,
							  QuerierService querierService)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.pivotMarketPostgreDAO = pivotMarketPostgreDAO;
		this.querierService = querierService;
	}


	public Set<CustomerFavorites> selectCustomerFavorites(CustomerIdentifier customerIdentifer)
	{
		
		int customerId = selectCustomerId(customerIdentifer);
		
		//String sql = "select cf.customerid, cf.productid, cf.count, p.productname from pivotalmarkets.customer_favorites cf, pivotalmarkets.product p where customerid = ? and cf.productid = p.productid";
		
		String sql = "SELECT customerid ,productid ,count\n" + 
		"FROM (\n" + 
		"  SELECT *\n" + 
		"        , max(count) OVER (PARTITION BY customerid) AS _max_\n" + 
		"        , row_number() OVER (PARTITION BY customerid, count ORDER BY random()) AS _rank_  -- include this line to randomly select one if ties unacceptable\n" + 
		"  FROM (select customerid, productid, count(*)\n" + 
		"    from (select o.customerid, i.productid, i.productname\n" + 
		"    from pivotalmarkets.orders o, pivotalmarkets.order_items i\n" +
		"    where o.orderid = i.orderid and customerid = ?) as custOrders\n" + 
		"    group by productid,customerid\n" + 
		"    order by customerid ) aggregateQuery\n" + 
		") foo\n" + 
		"WHERE count = _max_\n" + 
		"AND _rank_ = 1";

		
		System.out.println("selectCustomerFavorites sql:"+sql);
		
		RowMapper<CustomerFavorites> rm = (rs,rowNum) -> 
		{ 
			CustomerFavorites cp = new CustomerFavorites();
			cp.setCustomerId(customerId);
			
			Collection<ProductQuantity> productQuatities = new ArrayList<>();

			ProductQuantity productQuantity = new ProductQuantity();
			Product product = new Product();
			product.setProductName(this.selectProduct(rs.getInt("productid")).getProductName());
			productQuantity.setProduct(product);
			productQuantity.setQuantity(rs.getInt("count"));
			productQuatities.add(productQuantity);
		
			cp.setProductQuanties(productQuatities);
			return cp; 
		};

		List<CustomerFavorites> list =  jdbcTemplate.query(sql,rm,customerId);
		
		if(list == null ||  list.isEmpty())
			return null;
		
		return new HashSet<CustomerFavorites>(list);
	}//------------------------------------------------
	private int selectCustomerId(CustomerIdentifier customerIdentier)
	throws EmptyResultDataAccessException
	{
		String firstName = Text.initCaps(customerIdentier.getFirstName());
		String lastName = Text.initCaps(customerIdentier.getLastName());
		
		String sql = "select customerid from pivotalmarkets.customers c where (c.firstname = ? and c.lastname = ?) limit 1 ";
		// Object[] args, Class<T> requiredTypex
		int customerId = this.jdbcTemplate.queryForObject(sql,Integer.class,firstName,lastName);
		return customerId;
	}//------------------------------------------------

	public Set<Product> selectProductsByBeacon(Beacon beacon)
	{
	
		String sql = "select distinct c.categoryid, c.categoryname,c.subcategoryname,p.productid,p.productname, p.unit,p.cost,p.price \n" + 
		"from pivotalmarkets.beacon b, \n" + 
		"pivotalmarkets.category c,\n" + 
		"pivotalmarkets.product p\n" + 
		"where b.category = c.categoryname \n" + 
		"and \n" + 
		"( p.categoryid = c.categoryid or\n" + 
		"  p.subcategoryid = c.categoryid)\n" + 
		"  and b.uuid = ? or (b.major = ? and b.minor = ?)";
		
		System.out.println("sql:"+sql);
		RowMapper<Product> rm = (rs,rowNbr) -> 
		{
			Product p = new Product();
			p.setProductName(rs.getString("productname"));
			p.setPrice(rs.getBigDecimal("price"));
			p.setProductId(rs.getInt("productid"));
			p.setUnit(rs.getBigDecimal("unit"));
			return p;
		};
		
		Object[] args  = { beacon.getUuid(), beacon.getMajor(), beacon.getMinor()};
		List<Product> list =  jdbcTemplate.query(sql,args,rm);
		
		return list != null ? new HashSet<Product>(list) : null;
	}//------------------------------------------------
	public Set<Promotion> selectPromotionsByProduct(Product product)
	{
		if(product == null)
			return null;
		
		/*
		 * startdate
			enddate
			marketingmessage
			marketingimageurl

		 */
		String sql  = "select * from pivotalmarkets.promotion where productid = "+product.getProductId();
		
		System.out.println("sql:"+sql);
		
		RowMapper<Promotion> rm = (rs,rowNbr) -> 
		{ 
			Promotion p = new Promotion();
			p.setPromotionId(rs.getInt("promotionid"));
			p.setStartDate(new Date(rs.getDate("startdate").getTime()));
			p.setEndDate(new Date(rs.getDate("enddate").getTime()));
			p.setMarketingMessage(rs.getString("marketingmessage"));
			p.setMarketingUrl(rs.getString("marketingimageurl"));
			p.setProductId(rs.getInt("productid"));
			return p;
		};
		
		List<Promotion> list = jdbcTemplate.query(sql,rm);
		
		return list != null ? new HashSet<Promotion>(list) : null;
	}//------------------------------------------------
	public Product selectProduct(int productId)
	{
		return pivotMarketPostgreDAO.findProductById(productId);
	}//------------------------------------------------
	public List<Integer> selectProductIds()
	{
		//ResultSet rs, int rowNum
		RowMapper<Integer> mapper = (rs,rowNum) -> rs.getInt(1);
		
		return jdbcTemplate.query("select productid from pivotalmarkets.product",mapper);
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
			customerId = selectCustomerId(order.getCustomerIdentifier());
		}
		catch(EmptyResultDataAccessException e)
		{
			customerId = registerCustomerByIdentifer(order.getCustomerIdentifier());
			
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
			product = this.selectProduct(productId);
			products.add(product);

			this.jdbcTemplate.update(insertItemSql, orderId,productId,1,product.getProductName());
		}
		
		return products;
	}//------------------------------------------------
	
	private int registerCustomerByIdentifer(CustomerIdentifier customerIdentifier)
	{
		
		
		try
		{ 
			int customerid = this.selectCustomerId(customerIdentifier);
			if(customerid > 0)
				return -1; //already exists;
		}
		catch(EmptyResultDataAccessException e)
		{
		}
		Customer customer = new Customer();
		customer.setFirstName(Text.initCaps(customerIdentifier.getFirstName()));
		customer.setLastName(Text.initCaps(customerIdentifier.getLastName()));
		customer.setMobileNumber(customerIdentifier.getMobileNumber());
		int customerid = this.nextSeqVal("customer_seq");
		customer.setCustomerId(customerid);
		
		String sql = "INSERT INTO \"pivotalmarkets\".\"customers\" (customerid,firstname,lastname,mobilenumber) VALUES (?,?,?,?)";
		
		this.jdbcTemplate.update(sql, customer.getCustomerId(),customer.getFirstName(),customer.getLastName(),customer.getMobileNumber());
		
		return customerid;
	}//------------------------------------------------

	private int nextSeqVal(String sequenceName)
	{
		return this.jdbcTemplate.queryForObject("select nextval('"+sequenceName+"')", int.class);
	}//------------------------------------------------
	public Set<ProductAssociate> selectProductAssociates(Product product)
	{
		String sql = "select associations from pivotalmarkets.product_association where id = "+ SqlUtil.escape(product.getProductName());
		
		RowMapper<ProductAssociate> mapper = (rs,i) -> {
			String associations = rs.getString(1);
			String[] postsArray= associations != null? associations.split("|") : null;
			ProductAssociate pa = new ProductAssociate(product.getProductName(), postsArray);
			return pa;
			};
		
		List<ProductAssociate> list = this.jdbcTemplate.query(sql, mapper);
		
		if(list == null || list.isEmpty())
			return null;
		
		return new HashSet<>(list);
	}//------------------------------------------------

	public int updateCustomerFavorites()
	{
		//TODO: move to stored procedures
//		String sql = "\n" + 
//		"set search_path to pivotalmarkets;\n" + 
//		"\n" + 
//		"drop table if exists customer_favorites;\n" + 
//		"drop table if exists cp1;\n" + 
//		"create temp table cp1 as\n" + 
//		"    select o.customerid, i.productid, i.productname\n" + 
//		"    from orders o, order_items i\n" + 
//		"    where o.orderid = i.orderid;\n" + 
//		"\n" + 
//		"\n" + 
//		"drop table if exists cp2;\n" + 
//		"create temp table cp2 as\n" + 
//		"    select customerid, productid, count(*)\n" + 
//		"    from cp1\n" + 
//		"    group by productid,customerid\n" + 
//		"    order by customerid ;\n" + 
//		"\n" + 
//		"CREATE TABLE customer_favorites as\n" + 
//		"SELECT customerid ,productid ,count\n" + 
//		"FROM (\n" + 
//		"  SELECT *\n" + 
//		"        , max(count) OVER (PARTITION BY customerid) AS _max_\n" + 
//		"        , row_number() OVER (PARTITION BY customerid, count ORDER BY random()) AS _rank_  -- include this line to randomly select one if ties unacceptable\n" + 
//		"  FROM cp2\n" + 
//		") foo\n" + 
//		"WHERE count = _max_\n" + 
//		"AND _rank_ = 1;\n" + 
//		"\n" + 
//		"ALTER TABLE customer_favorites ADD COLUMN productname TEXT;\n" + 
//		"UPDATE customer_favorites c SET productname = (SELECT productname FROM product p WHERE p.productid = c.productid limit 1);\n";
//		
//		return this.jdbcTemplate.update(sql);
		
		return 0;
		
	}//------------------------------------------------


	public long queryOrderCount()
	{
		return this.jdbcTemplate.queryForObject(countSql,Long.class);
	}
}
