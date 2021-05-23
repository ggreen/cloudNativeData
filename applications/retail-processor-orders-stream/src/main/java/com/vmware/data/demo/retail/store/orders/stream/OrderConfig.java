package com.vmware.data.demo.retail.store.orders.stream;

import com.vmware.data.demo.retail.store.api.customer.CustomerDao;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import com.vmware.data.demo.retail.store.api.order.OrderMgmt;
import com.vmware.data.demo.retail.store.api.product.ProductJdbcDao;
import com.vmware.data.demo.retail.store.domain.CustomerFavorites;
import com.vmware.data.demo.retail.store.domain.Product;
import com.vmware.data.demo.retail.store.domain.ProductAssociate;
import com.vmware.data.demo.retail.store.domain.Promotion;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.geode.cache.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.Set;


@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class OrderConfig
{
    @Bean
	public OrderMgmt orderMgmt(OrderJdbcDAO orderDao,
							   Region<String, Set<CustomerFavorites>> customerFavoritesRegion,
							   Region<Integer, Set<ProductAssociate>> productAssociationsRegion, CustomerDao customerDao)
	{
		return new OrderMgmt(orderDao,
				customerFavoritesRegion,
				productAssociationsRegion,
				customerDao);
	}

    @Bean
	public OrderJdbcDAO orderJdbcDAO(JdbcTemplate jdbcTemplate, ProductJdbcDao productDao,
									 QuerierService querierService, CustomerDao customerFavoriteDao)
	{
		
		return new OrderJdbcDAO(jdbcTemplate, productDao, querierService,
				customerFavoriteDao);
		
	}//------------------------------------------------
	

	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) throws PropertyVetoException {
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
	   
	    

	    Properties props = new Properties();
	    props.setProperty("hibernate.dialect", org.hibernate.dialect.PostgreSQL9Dialect.class.getName());
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("io.pivotal.gemfire.domain");
	    factory.setDataSource(dataSource);
	    factory.setJpaProperties(props);
	    factory.afterPropertiesSet();

	    return factory.getObject();
	}//------------------------------------------------
	/**
	 * String url = "jdbc:postgresql://localhost/test";
	Properties props = new Properties();
	props.setProperty("user","fred");
	props.setProperty("password","");
	props.setProperty("ssl","true");
	Connection conn = DriverManager.getConnection(url, props);
	String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
	Connection conn = DriverManager.getConnection(url);
	jdbc:postgresql://host:port/database
	 * @param env the spring env context
	 * @return the data source
	 */
	@Bean
	public DataSource dataSource(Environment env)
	{
		return dataSource(
		env.getRequiredProperty("jdbcUrl"),
		env.getRequiredProperty("jdbcUsername"),
		env.getProperty("jdbcPassword")
		);
	}//------------------------------------------------

	public DataSource dataSource(String jdbcUrl, String userName,
	String password)
	{
		  // Construct BasicDataSource
		  BasicDataSource bds = new BasicDataSource();
		  bds.setDriverClassName("org.postgresql.Driver");
		  
		  bds.setUrl(jdbcUrl);
		  bds.setUsername(userName);
		  bds.setPassword(password);
		 // bds.setDefaultSchema("pivotalmarkets");
		  
		  return bds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource ds)
	{
		return new JdbcTemplate(ds);
	}
	
	@Bean("beaconProductsRegion")
	public Region<String, Set<Product>> beaconProductsRegion()
	{
		return GeodeClient.connect().getRegion("beaconProducts");
	}

	@Bean("customerFavoritesRegion")
	public Region<String, Set<CustomerFavorites>> customerFavoritesRegion()
	{
		return GeodeClient.connect().getRegion("customerFavorites");
	}//------------------------------------------------
	
	@Bean("beaconPromotionsRegion")
	public Region<String, Set<CustomerFavorites>> beaconPromotionsRegion()
	{
		return GeodeClient.connect().getRegion("beaconPromotions");
	}//------------------------------------------------
	@Bean("customerLocationRegion")
	Region<String,String> customerLocationRegion()
	{
		return GeodeClient.connect().getRegion("customerLocation");
	}
	
	@Bean("productsRegion")
	public Region<Integer,Product> productsRegion()
	{
		return GeodeClient.connect().getRegion("products");
	}
	
	@Bean("customerPromotionsRegion")
	public Region<String, Set<Promotion>> customerPromotionsRegion()
	{
		return GeodeClient.connect().getRegion("customerPromotions");
	}

	//Region<Integer, Set<ProductAssociate>> productAssociationsRegion;
	@Bean("productAssociationsRegion")
	public Region<Integer, Set<ProductAssociate>> productAssociationsRegion()
	{
		return GeodeClient.connect().getRegion("productAssociations");
	}

	@Bean
	public ProductJdbcDao productJdbcDao(JdbcTemplate jdbcTemplate )
	{
		return new ProductJdbcDao(jdbcTemplate);
	}

	@Bean
	public CustomerDao customerDao(ProductJdbcDao productJdbcDao,JdbcTemplate jdbcTemplate )
	{
		return new CustomerDao(productJdbcDao,jdbcTemplate);
	}

	@Bean
	public QuerierService querierService()
	{
		return GeodeClient.connect().getQuerierService();
	}
}
