package io.pivotal.pde.pivotMart.streams;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import gedi.solutions.geode.client.GeodeClient;

@Ignore
public class TempTest
{
	@Test
	public void test() throws Exception
	{
		GeodeClient
			.connect()
				.getRegion("test").put("test","test");
	}

}
