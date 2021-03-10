package com.orderbook.server;

import com.orderbook.core.OrderBooks;
import com.orderbook.model.OrderBookEntry;
import com.orderbook.model.OrderBookSnapshot;
import com.orderbook.model.SnapshotOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderBookService {

	private final OrderBooks orderBooks;

	public void update(OrderBookEntry orderBookEntry) {
		orderBooks.update(orderBookEntry);
	}

	public Optional<OrderBookSnapshot> get(String exchange, String symbol) {
		return orderBooks
				.get(exchange, symbol)
				.map(orderBook -> {
                    List<SnapshotOrder> buy = orderBook.buyOrders()
							.stream()
							.map(x -> SnapshotOrder.builder()
									.price(x.getPrice())
									.quantity(x.getQuantity())
									.build()).collect(Collectors.toList());

					List<SnapshotOrder> sells = orderBook.sellOrders()
							.stream()
							.map(x -> SnapshotOrder.builder()
									.price(x.getPrice())
									.quantity(x.getQuantity())
									.build()).collect(Collectors.toList());

					return OrderBookSnapshot.builder()
							.asks(sells)
							.bids(buy)
							.build();

				});
	}
}
