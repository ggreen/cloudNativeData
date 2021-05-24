package com.vmware.data.demo.retail.store.orders.stream;

import com.vmware.data.demo.retail.store.api.customer.CustomerDao;
import com.vmware.data.demo.retail.store.api.order.OrderJdbcDAO;
import com.vmware.data.demo.retail.store.api.product.ProductJdbcDao;
import io.pivotal.services.dataTx.geode.io.QuerierService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
public class JdbcConfig
{

    @Bean
    public OrderJdbcDAO orderJdbcDAO(JdbcTemplate jdbcTemplate, ProductJdbcDao productDao,
                                     QuerierService querierService, CustomerDao customerFavoriteDao)
    {

        return new OrderJdbcDAO(jdbcTemplate, productDao, querierService,
                customerFavoriteDao);

    }//------------------------------------------------


    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) throws PropertyVetoException
    {
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
}
