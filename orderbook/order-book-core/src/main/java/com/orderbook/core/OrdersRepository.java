package com.orderbook.core;

import com.orderbook.model.OrderSide;

import java.util.List;

public interface OrdersRepository {
	 void add(Order order);
	 Order fulfil(Order order);
	 void remove(Order order);
	 List<Order> getAll(OrderSide orderSide);
}
