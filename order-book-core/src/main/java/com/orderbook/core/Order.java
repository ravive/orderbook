package com.orderbook.core;

import com.orderbook.model.OrderBookEntry;
import com.orderbook.model.OrderSide;
import com.orderbook.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
	private Double price;
	private Double quantity;
	private Long   time;
	@Builder.Default
	private OrderStatus status = OrderStatus.New;
	private OrderSide side;

	public static Order from(OrderBookEntry orderBookEntry) {
		return Order
				.builder()
				.price(orderBookEntry.getPrice())
				.quantity(orderBookEntry.getQuantity())
				.time(orderBookEntry.getTimestamp())
				.side(orderBookEntry.getSide())
				.status(orderBookEntry.getStatus())
				.build();
	}

	public void decrease(Double availableQuantity) {
		this.quantity = this.quantity - availableQuantity;
	}

	@Override
	public String toString() {
		String type =  OrderSide.BUY.equals(side) ? "BID" : "ASK";
		return type + " " +price + " " + quantity;
	}
}
