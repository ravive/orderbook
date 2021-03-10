package com.orderbook.server;

import com.orderbook.model.OrderBookEntry;
import com.orderbook.model.OrderBookSnapshot;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class OrderBookController {

	private final OrderBookService orderBookService;

	@RequestMapping(value = "/orderbook/update", method = RequestMethod.POST)
	public ResponseEntity updateEntry(@RequestBody OrderBookEntry orderBookEntry) {
		orderBookService.update(orderBookEntry);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/orderbook/fetch")
	public ResponseEntity<OrderBookSnapshot> getSnapShotByType(@RequestParam("exchange") String exchange, @RequestParam("symbol") String symbol) {
		return orderBookService.get(exchange, symbol)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
