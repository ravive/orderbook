package com.orderbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderBookSnapshot {
	private List<SnapshotOrder> asks;
	private List<SnapshotOrder> bids;
}
