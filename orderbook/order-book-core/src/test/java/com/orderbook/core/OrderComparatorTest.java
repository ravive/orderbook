package com.orderbook.core;


import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class OrderComparatorTest {

	@Test
	public void preferLowTimeWhenPriceIsEqual() {
		Order order1 = Order.builder().price(1d).time(1L).build();
		Order order2 = Order.builder().price(1d).time(2L).build();

		assertThat(new OrderComparator().compare(order1,order2)).isEqualTo(-1);
	}

	@Test
	public void preferLowPrice() {
		Order order1 = Order.builder().price(2d).time(1L).build();
		Order order2 = Order.builder().price(1d).time(2L).build();

		assertThat(new OrderComparator().compare(order1,order2)).isEqualTo(1);
	}
}
