package com.vmware.data.demo.retail.store.api.order;


import com.vmware.data.demo.retail.store.api.order.RetailOrderCsvBuilder;
import io.pivotal.gemfire.domain.CustomerIdentifier;
import nyla.solutions.core.io.IO;
import org.junit.jupiter.api.Test;

import static org.jgroups.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RetailOrderCsvBuilderTest
{

	@Test
	public void test_parseLineUserProfiler()
	{
		RetailOrderCsvBuilder builder = new RetailOrderCsvBuilder();
		
		String csv = "id,firstName,lastName.email,phone";
		try
		{
			builder.buildOrderLine(csv);
			fail();
		}
		catch(IllegalArgumentException e)
		{}
		
		csv = "id,firstName,lastName,email,phone,\"1,2,3\"";
		builder.buildOrderLine(csv);
		
		CustomerIdentifier user = builder.getCustomerIdentifier();
		
		assertEquals("email", user.getEmail());
		assertEquals("phone", user.getMobileNumber());
		assertEquals("firstName", user.getFirstName());
		assertEquals("lastName", user.getLastName());
		assertEquals("id", user.getKey());
		
	}
	
	@Test
	public void test_clean_csv_http()
	throws Exception
	{
		String text = IO.readFile("src/test/resources/raw_http.txt");
		
		RetailOrderCsvBuilder builder = new RetailOrderCsvBuilder();
		
		String out = builder.cleanCsv(text);
		
		assertTrue(out,!out.contains("contentType"));
		assertTrue(out,!out.contains("charset"));
		
	}

}
