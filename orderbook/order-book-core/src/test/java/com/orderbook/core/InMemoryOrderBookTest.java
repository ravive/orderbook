package com.orderbook.core;

import com.orderbook.model.OrderSide;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class InMemoryOrderBookTest {


	@Test
	public void preferSellByPrice() {
		OrderBook orderBook = new OrderBook(new InMemoryOrdersRepository());
		orderBook.execute(Order.builder().price(315.7d).quantity(52.78d).time(1500717600563L).side(OrderSide.BUY).build());
		orderBook.execute(Order.builder().price(315d).quantity(100d).time(1500717601063L).side(OrderSide.BUY).build());
		orderBook.execute(Order.builder().price(317.5d).quantity(35.5d).time(1500717601083L).side(OrderSide.SELL).build());
		assertThat(orderBook.buyOrders().get(0).getQuantity()).isEqualTo(64.5);
		assertThat(orderBook.buyOrders().get(1).getQuantity()).isEqualTo(52.78d);
		assertThat(orderBook.sellOrders().size()).isEqualTo(0);
	}
}
