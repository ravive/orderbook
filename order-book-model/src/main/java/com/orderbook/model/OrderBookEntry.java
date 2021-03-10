package com.orderbook.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class OrderBookEntry {
	private Long timestamp;
	private String exchange;
	private String symbol;
	private OrderSide side;
	private Double price;
	private Double quantity;
	@Builder.Default
	private OrderStatus status = OrderStatus.New;
	private OrderType type;
}
