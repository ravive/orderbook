package com.orderbook.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class OrderBookType {
	private String exchange;
	private String symbol;
}
