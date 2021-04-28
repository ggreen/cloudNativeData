package io.pivotal.services.pivotMart.streams;

import java.nio.charset.StandardCharsets;

import nyla.solutions.core.io.IO;

public class GenerateSampleData
{
	static int productid = 1;

	public static void main(String[] args)
	throws Exception
	{
		

		String beveragesCategory = "1";
		String fruitsCategory = "2";
		String produceCategory= "3";
		
		
		String[] beverages= IO.readLines("src/test/resources/beverages.txt", StandardCharsets.UTF_8);
		constructInsertProduct( beverages,beveragesCategory);
			
		String[] fruits= IO.readLines("src/test/resources/fruits.txt", StandardCharsets.UTF_8);
		constructInsertProduct( fruits,fruitsCategory);
		
		String[] produces = IO.readLines("src/test/resources/produces.txt", StandardCharsets.UTF_8);
		constructInsertProduct( produces, produceCategory);
	}//------------------------------------------------
	private static void constructInsertProduct( String[] productNames,String beveragesCategory)
	{
		for (String product : productNames)
		{
			if(product == null ||  product.trim().length() == 0)
				continue;
			
			build(product,beveragesCategory);
		}
	}//------------------------------------------------
	private static void build(String productname,String category)
	{
		String format = String.format("INSERT INTO \"pivotalmarkets\".\"product\" (productid,productname,categoryid,subcategoryid,unit,cost,price,startdate,enddate,createddate,lastupdateddate) VALUES (%s,'%s',%s,%s,null,null,null,null,null,null,null);",
		String.valueOf(productid++),productname,category,category);
		
		System.out.println(format);
	}
}
