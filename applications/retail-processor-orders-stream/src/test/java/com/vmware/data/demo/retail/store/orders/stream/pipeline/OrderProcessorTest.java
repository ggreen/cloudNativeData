package com.vmware.data.demo.retail.store.orders.stream.pipeline;

import io.pivotal.gemfire.domain.OrderDTO;
import io.pivotal.market.api.PivotalMartFacadeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorTest
{
	@Mock
	private PivotalMartFacadeService service;

	@Test
	public void testProcess()
	{
		var op = new OrderProcessor(service);
		
		Collection<OrderDTO> dto = Collections.singleton(new OrderDTO());
		when(service.processOrderCSV(anyString())).thenReturn(dto);
		
		String csv = "\"0\",\"Nyla\",\"Nyla\",\"777-777-7777\",\"1,2\"";
		
		Collection<OrderDTO> orders = op.apply(csv);

		assertNotNull(orders);
	}

}
