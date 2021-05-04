package com.vmware.data.demo.retail.store.orders.stream;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.market.api.PivotalMartFacadeService;
import nyla.solutions.core.util.Config;


@SpringBootApplication
public class OrderProcessorRetailApp
{
	 
	/**
	 * Add argument to Configurations and Run the spring boot app 
	 * @param args the input args include --LOCATORS=host[port] --SECURITY_USERNAME=u --SECURITY_PASSWORD=p
	 */
	public static void main(String[] args) {
		Config.loadArgs(args);
		SpringApplication.run(OrderProcessorRetailApp.class, args);
	}//------------------------------------------------
	

}