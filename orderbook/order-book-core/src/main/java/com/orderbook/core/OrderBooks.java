package com.orderbook.core;

import com.orderbook.model.OrderBookEntry;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class OrderBooks {

	private final Map<OrderBookType, OrderBook> orderBookMap;
    private final OrderBookRepositoryFactory    orderBookRepositoryFactory;

	public OrderBooks(OrderBookRepositoryFactory orderBookRepositoryFactory) {
		this.orderBookRepositoryFactory = orderBookRepositoryFactory;
		this.orderBookMap = new ConcurrentHashMap<>();
	}

	public void update(OrderBookEntry orderBookEntry) {
		OrderBookType  orderBookType = OrderBookType.builder()
				.exchange(orderBookEntry.getExchange())
				.symbol(orderBookEntry.getSymbol())
				.build();
		this.orderBookMap.putIfAbsent(orderBookType,new OrderBook(orderBookRepositoryFactory.create()));
		this.orderBookMap.get(orderBookType).execute(Order.from(orderBookEntry));
	}

	public Optional<OrderBook> get(String exchange, String symbol) {
		OrderBookType  orderBookType = OrderBookType.builder()
				.exchange(exchange)
				.symbol(symbol)
				.build();

		return Optional.ofNullable(orderBookMap.getOrDefault(orderBookType,null));

	}
}