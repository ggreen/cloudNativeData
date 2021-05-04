package com.vmware.data.demo.retail.store.orders.stream;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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
import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.market.api.PivotMartMgr;
import io.pivotal.market.api.PivotalMartFacadeService;
import io.pivotal.market.api.dao.PivotMarketPostgreDAO;
import io.pivotal.market.api.dao.PivotMartDAO;


@Configuration
@EnableJpaRepositories(basePackages = "io.pivotal.market.api")
@EnableTransactionManagement
public class OrderConfig
{
    @Bean
	public PivotalMartFacadeService pivotMartFacadeService()
	{
		return new PivotMartMgr();
	}

    @Bean
	public PivotMarketPostgreDAO postreDao()
	{
		
		return new PivotMarketPostgreDAO();
		
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
	
	//productsRegion
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
	public PivotMartDAO dao(DataSource dataSource)
	{
		PivotMartDAO pivotMartDAO = new PivotMartDAO();
		pivotMartDAO.setJdbcTemplate(this.jdbcTemplate(dataSource));
		
		return pivotMartDAO;
	}//------------------------------------------------
	
	@Bean
	QuerierService querierService()
	{
		return GeodeClient.connect().getQuerierService();
	}
}
