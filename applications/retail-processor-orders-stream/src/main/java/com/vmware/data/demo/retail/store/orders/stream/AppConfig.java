package com.vmware.data.demo.retail.store.orders.stream;

import com.vmware.data.demo.retail.store.api.customer.CustomerDao;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import com.vmware.data.demo.retail.store.api.order.OrderMgmt;
import com.vmware.data.demo.retail.store.domain.CustomerFavorites;
import com.vmware.data.demo.retail.store.domain.ProductAssociate;
import org.apache.geode.cache.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Set;


@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class AppConfig
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
}
