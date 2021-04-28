package com.vmware.data.services.demo.cloudNativeData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan
//		(basePackages = {"io.pivotal.pde.demo.cloudNativeData"})
@SpringBootApplication
public class RetailStoreApp
{
	public static void main(String[] args)
	{
		SpringApplication.run(RetailStoreApp.class, args);
	}
}
