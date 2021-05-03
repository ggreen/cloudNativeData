package io.pivotal.pde.pivotMarket.streams.processors.orders;

import io.pivotal.market.api.PivotMartMgr;
import io.pivotal.pde.pivotMarket.streams.processors.orders.OrderProcessorRetailApp.OrderProcessor;

public class OrderIT
{

	public static void main(String[] args)
	{
		OrderProcessor op = new OrderProcessor();
		
		OrderConfig conf = new OrderConfig();
		
		PivotMartMgr mgr = (PivotMartMgr) conf.pivotMartFacadeService();
		
	
		op.service = mgr;
		
		
		String csv = "\"0\",\"Nyla\",\"Nyla\",\"777-777-7777\",\"1,2\"";
		
		op.service.processOrderCSV(csv);
	}
}
