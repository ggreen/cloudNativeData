package com.vmware.data.demo.retail.store.orders.stream;

import nyla.solutions.core.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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