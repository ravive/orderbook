package com.orderbook.core;

import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {

	public int compare(Order order, Order order2) {
		if (order.getPrice().equals(order2.getPrice())) {
			return order.getTime().compareTo(order2.getTime());
		}
		return order.getPrice().compareTo(order2.getPrice());
	}
}
