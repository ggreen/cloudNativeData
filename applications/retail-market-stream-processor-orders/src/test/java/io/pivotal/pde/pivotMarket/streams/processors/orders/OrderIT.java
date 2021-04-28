package io.pivotal.pde.pivotMarket.streams.processors.orders;

import io.pivotal.market.api.PivotMartMgr;
import io.pivotal.market.api.dao.PivotMartDAO;
import io.pivotal.pde.pivotMarket.streams.processors.orders.OrderProcessorPivotMartApp.OrderProcessor;

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
