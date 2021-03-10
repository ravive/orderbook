package com.orderbook.core;

import com.orderbook.model.OrderSide;
import org.junit.Test;


import static org.assertj.core.api.Java6Assertions.assertThat;

public class InMemoryOrdersRepositoryTest {

	@Test
	public void whenNoOrdersReturnZero() {
		InMemoryOrdersRepository inMemoryOrdersRepository = new InMemoryOrdersRepository();
		Order                    order                    = inMemoryOrdersRepository.fulfil(Order.builder().price(10d).quantity(5d).build());
		assertThat(order.getQuantity()).isEqualTo(5);
	}

	@Test
	public void whenPriceIsFulfilQuantityIsZero() {
		InMemoryOrdersRepository inMemoryOrdersRepository = new InMemoryOrdersRepository();
		inMemoryOrdersRepository.add(Order.builder().price(4.5d).quantity(5d).side(OrderSide.BUY).build());
		inMemoryOrdersRepository.add(Order.builder().price(10d).quantity(5d).side(OrderSide.BUY).build());
		inMemoryOrdersRepository.add(Order.builder().price(5d).quantity(5d).side(OrderSide.BUY).build());

		Order order = inMemoryOrdersRepository.fulfil(Order.builder().price(5d).quantity(7d).side(OrderSide.SELL).build());
		assertThat(order.getQuantity()).isEqualTo(0);
	}
}
