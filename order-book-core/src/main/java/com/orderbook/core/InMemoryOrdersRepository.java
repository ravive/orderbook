package com.orderbook.core;

import com.orderbook.model.OrderSide;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class InMemoryOrdersRepository implements OrdersRepository,OrderBookRepositoryFactory {

	private final Map<OrderSide,Map<Double, Double>> priceToQuantity;

	public InMemoryOrdersRepository() {
		priceToQuantity = new HashMap<>();
		priceToQuantity.put(OrderSide.SELL,new ConcurrentSkipListMap(Comparator.naturalOrder()));
		priceToQuantity.put(OrderSide.BUY,new ConcurrentSkipListMap(Comparator.naturalOrder()));
	}

	public void add(Order order) {
		this.priceToQuantity.get(order.getSide()).putIfAbsent(order.getPrice(), 0d);
		this.priceToQuantity.get(order.getSide()).computeIfPresent(order.getPrice(), (key, value) -> value + order.getQuantity());
	}

	public Order fulfil(Order order) {

		Order orderToFulfilled = Order.builder()
				.quantity(order.getQuantity())
				.price(order.getPrice())
				.build();

		OrderSide otherSide = OrderSide.BUY.equals(order.getSide()) ? OrderSide.SELL : OrderSide.BUY;
		priceToQuantity
				.get(otherSide  )
				.keySet()
				.stream()
				.takeWhile(price -> price <= order.getPrice() && orderToFulfilled.getQuantity() > 0)
				.forEach(price -> {
					Double reductQuantity = Math.min(priceToQuantity.get(otherSide).get(price), orderToFulfilled.getQuantity());
					Double newQuantity = priceToQuantity.get(otherSide).computeIfPresent(price, (key, value) -> value - reductQuantity);
					if (newQuantity == 0) {
						priceToQuantity.get(otherSide).remove(price);
					}
					orderToFulfilled.decrease(reductQuantity);
				});


		return orderToFulfilled;
	}

	public void remove(Order order) {
		priceToQuantity.get(order.getSide()).computeIfPresent(order.getPrice(),(key,value) -> value - order.getQuantity());
	}

	@Override
	public List<Order> getAll(OrderSide orderSide) {
		return priceToQuantity
				.get(orderSide)
				.keySet()
				.stream()
				.map(x -> Order.builder()
						.side(orderSide)
						.price(x)
						.quantity(priceToQuantity.get(orderSide).get(x))
						.build()).collect(Collectors.toList());
	}

	@Override
	public OrdersRepository create() {
		return new InMemoryOrdersRepository();
	}
}
