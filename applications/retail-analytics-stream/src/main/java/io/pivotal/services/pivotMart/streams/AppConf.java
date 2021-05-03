package io.pivotal.services.pivotMart.streams;

import io.pivotal.gemfire.domain.BeaconRequest;
import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.services.pivotMart.streams.consumers.BeaconRequestConsumer;
import io.pivotal.services.pivotMart.streams.consumers.OrderConsumer;
import nyla.solutions.core.patterns.workthread.ExecutorBoss;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.function.Consumer;

@Configuration
@EnableTransactionManagement
@EnableScheduling
//@EnableBinding(Source.class)
public class AppConf
{


		//checkOrderQueue
//	@Bean
//	public Thread checkOrderQueueThread(PivotMartStreamService service,Environment e)
//	{
//		Thread t = new Thread(
//		() ->{
//			while(true)
//			{
//				try
//				{
//					Thread.sleep(e.getProperty("checkKafkaSleepMs",Long.class));
//					service.checkOrderQueue();
//				}
//				catch (InterruptedException e1)
//				{
//					e1.printStackTrace();
//				}
//				catch(Exception  exp)
//				{
//					exp.printStackTrace();
//				}
//			}
//
//		});
//
//		t.start();
//		return t;
//	}//------------------------------------------------
//	@Bean
//	public Thread checkBeaconRequestThread(PivotMartStreamService service,Environment e)
//	{
//		Thread t = new Thread(
//		() ->{
//			while(true)
//			{
//				try
//				{
//					Thread.sleep(e.getProperty("checkKafkaSleepMs",Long.class));
//					service.checkBeaconRequestQueue();
//				}
//				catch (InterruptedException e1)
//				{
//					e1.printStackTrace();
//				}
//				catch(Exception  exp)
//				{
//					exp.printStackTrace();
//				}
//			}
//
//		});
//
//		t.start();
//		return t;
//	}//------------------------------------------------
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
    public Consumer<OrderDTO> orders(RetailStreamAnalyticsService streamService) {
        return new OrderConsumer(streamService);
    }

    @Bean
    public Consumer<BeaconRequest> beaconRequests(RetailStreamAnalyticsService streamService)
    {
        return new BeaconRequestConsumer(streamService);
    }

}
