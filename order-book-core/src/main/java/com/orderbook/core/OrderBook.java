package com.orderbook.core;


import com.orderbook.model.OrderSide;
import com.orderbook.model.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderBook {

	private final OrdersRepository ordersRepository;

	public OrderBook( OrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}

	public List<Order> buyOrders() {
		return ordersRepository.getAll(OrderSide.BUY);
	}

	public List<Order> sellOrders() {
		return ordersRepository.getAll(OrderSide.SELL);
	}

	private void fulfil(Order buyOrder) {
		Order fulfilOrder = ordersRepository.fulfil(buyOrder);
		if (fulfilOrder.getQuantity() > 0) {
			this.ordersRepository.add(buyOrder);
		}
	}

	public void cancel(Order order) {
		ordersRepository.remove(order);
	}



	@Override
	public String toString() {
		return  this.ordersRepository
				.getAll(OrderSide.SELL)
				.stream()
				.map(Order::toString)
				.collect(Collectors.joining("\n"))+ "\n" +
				this.ordersRepository.getAll(OrderSide.BUY)
						.stream()
						.map(Order::toString)
						.collect(Collectors.joining("\n"));
	}

	public void execute(Order order) {

		if (OrderStatus.Cancel.equals(order.getStatus())) {
			this.cancel(order);
			return;
		}

		if (OrderStatus.New.equals(order.getStatus())) {
			this.fulfil(order);
			return;
		}


	}
}
