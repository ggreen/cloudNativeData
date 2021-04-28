package io.pivotal.market.api;

import static org.junit.Assert.*;

import com.vmware.data.retail.store.domain.CustomerIdentifier;
import org.junit.Test;

import nyla.solutions.core.io.IO;
public class MarketCsvBuilderTest
{

	@Test
	public void test_parseLineUserProfiler()
	{
		MarketCsvBuilder builder = new MarketCsvBuilder();
		
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
		
		MarketCsvBuilder builder = new MarketCsvBuilder();
		
		String out = builder.cleanCsv(text);
		
		assertTrue(out,!out.contains("contentType"));
		assertTrue(out,!out.contains("charset"));
		
	}

}
