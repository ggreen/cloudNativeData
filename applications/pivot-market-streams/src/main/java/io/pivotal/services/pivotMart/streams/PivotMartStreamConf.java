package io.pivotal.services.pivotMart.streams;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.pivotal.gemfire.domain.CustomerFavorites;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.market.api.dao.PivotMarketPostgreDAO;
import io.pivotal.services.pivotMart.streams.dao.PivotMartDAO;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import solutions.nyla.apacheKafka.ApacheKafka;

@Configuration
@EnableTransactionManagement
@EnableScheduling
public class PivotMartStreamConf
{


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
		//checkOrderQueue

	@Bean
	public Thread checkOrderQueueThread(PivotMartStreamService service,Environment e)
	{
		Thread t = new Thread(
		() ->{
			while(true)
			{
				try
				{
					Thread.sleep(e.getProperty("checkKafkaSleepMs",Long.class));
					service.checkOrderQueue();
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch(Exception  exp)
				{
					exp.printStackTrace();
				}
			}
			
		});
		
		t.start();
		return t;
	}//------------------------------------------------
	@Bean
	public Thread checkBeaconRequestThread(PivotMartStreamService service,Environment e)
	{
		Thread t = new Thread(
		() ->{
			while(true)
			{
				try
				{
					Thread.sleep(e.getProperty("checkKafkaSleepMs",Long.class));
					service.checkBeaconRequestQueue();
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch(Exception  exp)
				{
					exp.printStackTrace();
				}
			}
			
		});
		
		t.start();
		return t;
	}//------------------------------------------------
	/**
	 * Working thread executor pattern
	 * @param env the spring configurations
	 * @return the executor boss
	 */
	@Bean
	public ExecutorBoss boss(Environment env)
	{
		return new ExecutorBoss(env.getProperty("bossThreads",Integer.class,10));
	}//------------------------------------------------
	
	@Bean
	public ApacheKafka apacheKafka(Environment env)
	{
		//String bootStrapServersConfig, String groupId
		return ApacheKafka.connect(
		env.getRequiredProperty("kafkaBootStrapServers"),
		env.getRequiredProperty("kafkaGroupId")
		);
	}
	@Bean("beaconRequestQueue")
	public BlockingQueue<String> beaconRequestQueue(ApacheKafka apacheKafka)
	{
		return apacheKafka.queue("beacon");
	}
	
	@Bean("orderQueue")
	public BlockingQueue<String> orderQueue(ApacheKafka apacheKafka)
	{
		return apacheKafka.queue("orders");
	}//------------------------------------------------
	
	
	@Bean
	QuerierService querierService()
	{
		return GeodeClient.connect().getQuerierService();
	}
}
