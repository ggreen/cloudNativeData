package io.pivotal.pde.demo.cloudNativeData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"io.pivotal.pde.demo.cloudNativeData"})
@SpringBootApplication
public class PivotMarketApp
{
	public static void main(String[] args)
	{
		SpringApplication.run(PivotMarketApp.class, args);
	}
}
