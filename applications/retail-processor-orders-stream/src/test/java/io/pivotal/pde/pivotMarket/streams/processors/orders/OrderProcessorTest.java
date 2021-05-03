package io.pivotal.pde.pivotMarket.streams.processors.orders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.market.api.PivotalMartFacadeService;
import io.pivotal.pde.pivotMarket.streams.processors.orders.OrderProcessorRetailApp.OrderProcessor;

public class OrderProcessorTest extends OrderProcessor
{

	@Test
	public void testProcess()
	{
		OrderProcessor op = new OrderProcessor();
		
		op.service = mock(PivotalMartFacadeService.class);

		Collection<OrderDTO> dto = Collections.singleton(new OrderDTO());
		when(op.service.processOrderCSV(anyString())).thenReturn(dto);
		
		String csv = "\"0\",\"Nyla\",\"Nyla\",\"777-777-7777\",\"1,2\"";
		
		Collection<OrderDTO> orders = op.process(csv);
		
	
		assertNotNull(orders);
		
	}

}
